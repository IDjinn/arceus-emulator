package habbo.rooms.components.objects.items;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.furniture.FurnitureType;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.furniture.extra.data.IExtraData;
import habbo.habbos.IHabboManager;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.floor.DefaultFloorItem;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.floor.interactions.LayFloorItem;
import habbo.rooms.components.objects.items.floor.interactions.RollerFloorItem;
import habbo.rooms.components.objects.items.floor.interactions.SitFloorItem;
import habbo.rooms.components.objects.items.wall.DefaultWallItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;
import utils.pathfinder.Position;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RoomItemFactory implements IRoomItemFactory {
    private final Injector injector;
    public final Map<String, Class<? extends IRoomItem>> itemDefinitionMap;
    public final Map<String, Constructor<? extends IRoomItem>> itemConstructorCache;
    private final IFurnitureManager furnitureManager;
    private final IHabboManager habboManager;
    private final Logger logger = LogManager.getLogger();

    @Inject
    public RoomItemFactory(IFurnitureManager furnitureManager, IHabboManager habboManager, Injector injector) {
        this.furnitureManager = furnitureManager;
        this.habboManager = habboManager;
        this.injector = injector;
        this.itemDefinitionMap = new HashMap<>();
        this.itemConstructorCache = new HashMap<>();

        this.initInteractions();
    }

    private void initInteractions() {
        this.itemDefinitionMap.put(DefaultFloorItem.INTERACTION_NAME, DefaultFloorItem.class);
        this.itemDefinitionMap.put(DefaultWallItem.INTERACTION_NAME, DefaultWallItem.class);
        this.itemDefinitionMap.put(SitFloorItem.INTERACTION_NAME, SitFloorItem.class);
        this.itemDefinitionMap.put(LayFloorItem.INTERACTION_NAME, LayFloorItem.class);
        this.itemDefinitionMap.put(RollerFloorItem.INTERACTION_NAME, RollerFloorItem.class);
        // TODO: DETECT DUPLICATE CLASSES/INTERACTION
        this.logger.info(STR."RoomItemFactory initialized with total of \{this.itemDefinitionMap.size()} interactions");
    }

    @Override
    public IRoomItem create(IRoomItemData itemData, IRoom room) {
        var furnitureData = this.furnitureManager.get(itemData.getFurnitureId());
        if (furnitureData == null)
            throw new IllegalArgumentException(STR."Furniture data  not found for id \{itemData.getFurnitureId()}");

        var item = switch (furnitureData.getType()) {
            case FLOOR -> createFloorObject(itemData, room, furnitureData);
            case WALL -> createWallObject(itemData, room, furnitureData);
            case null, default ->
                    throw new IllegalArgumentException(STR."Furniture type \{furnitureData.getType().toString()} is not valid object");
        };
        this.injector.injectMembers(item);

        var ownerData = this.habboManager.getHabboData(itemData.getOwnerId());
        ownerData.ifPresent(item::setOwnerData);
        return item;
    }

    @Override
    public IRoomItem create(IConnectionResult result, IRoom room) throws Exception {
        var itemData = createItemDataFromResult(result);
        return create(itemData, room);
    }

    private IFloorItem createFloorObject(IRoomItemData data, IRoom room, IFurniture furnitureData) {
        var object = createRoomObject(data, room, furnitureData);
        if (object == null)
            throw new IllegalArgumentException(STR."Furniture type \{furnitureData.getId()} couldn't be created with interaction type \{furnitureData.getInteractionType()}. No constructors were found.");

        return (IFloorItem) object;
    }

    private IWallItem createWallObject(IRoomItemData data, IRoom room, IFurniture furnitureData) {
        var object = createRoomObject(data, room, furnitureData);
        if (object == null)
            throw new IllegalArgumentException(STR."Furniture type \{furnitureData.getId()} couldn't be created with interaction type \{furnitureData.getInteractionType()}. No constructors were found.");

        return (IWallItem) object;
    }

    private @Nullable IRoomItem createRoomObject(IRoomItemData data, IRoom room, IFurniture furniture) {
        assert furniture != null;
        assert room != null;

//        if (furniture.canSit())

        // TODO:GIFTS

        if (this.itemDefinitionMap.containsKey(furniture.getInteractionType())) {
            try {
                Constructor<? extends IRoomItem> constructor;

                if (this.itemConstructorCache.containsKey(furniture.getInteractionType())) {
                    constructor = this.itemConstructorCache.get(furniture.getInteractionType());
                } else {
                    constructor = this.itemDefinitionMap.get(furniture.getInteractionType()).getConstructor(IRoomItemData.class, IRoom.class, IFurniture.class);
                    this.itemConstructorCache.put(furniture.getInteractionType(), constructor);
                }

                if (constructor != null)
                    return constructor.newInstance(data, room, furniture);
            } catch (Exception e) {
                this.logger.warn(STR."Failed to create instance for item: \{furniture.getId()}, type: \{furniture.getInteractionType()}", e);
            }
        }

        this.logger.warn("interaction type {} was not found to create instance for item {}", furniture.getInteractionType(), data.getId());
        if (furniture.getType().equals(FurnitureType.FLOOR))
            return new DefaultFloorItem(data, room, furniture);
        if (furniture.getType().equals(FurnitureType.WALL))
            return new DefaultWallItem(data, room, furniture);

        return null;
    }

    private IRoomItemData createItemDataFromResult(IConnectionResult result) throws Exception {
        var id = result.getInt("id");
        var itemId = result.getInt("item_id");
        var ownerId = result.getInt("user_id");
        var x = result.getInt("x");
        var y = result.getInt("y");
        var z = result.getDouble("z");
        var rotation = result.getInt("rot");
        var wallPosition = result.getString("wall_pos");

        var extraData = this.furnitureManager.parseExtraData(result.getString("extra_data"));
        extraData.setLimitedData(LimitedData.fromString(result.getString("limited_data")));

        return new RoomItemData(id, itemId, ownerId, new Position(x, y, z), rotation, extraData, wallPosition);
    }

    @Override
    public IRoomItemData createItemData(int id, int furnitureId, int ownerId, Position position, int rotation, IExtraData extraData) {
        return new RoomItemData(
                id,
                furnitureId,
                ownerId,
                position,
                rotation,
                extraData,
                ""
        );
    }

    @Override
    public IRoomItemData createItemData(int id, int furnitureId, int ownerId, String wallPosition, IExtraData extraData) {
        return new RoomItemData(
                id,
                furnitureId,
                ownerId,
                new Position(-1, -1, 0d),
                0,
                extraData,
                wallPosition
        );
    }
}
