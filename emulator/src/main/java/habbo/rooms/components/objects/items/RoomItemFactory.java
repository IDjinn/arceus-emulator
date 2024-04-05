package habbo.rooms.components.objects.items;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.furniture.FurnitureType;
import habbo.furniture.IFurniture;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.IHabboManager;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.floor.DefaultFloorItem;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.DefaultWallItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import io.netty.util.internal.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;
import utils.Position;

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
    private Logger logger = LogManager.getLogger();

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

        logger.info(STR."RoomItemFactory initialized with total of \{this.itemDefinitionMap.size()} interactions");
    }


    @Override
    public IRoomItem create(IConnectionResult result, IRoom room) throws Exception {
        var itemData = createItemDataFromResult(result);
        var furnitureData = furnitureManager.get(itemData.getFurnitureId());
        if (furnitureData == null)
            throw new IllegalArgumentException(STR."Furniture data  not found for id \{itemData.getFurnitureId()}");

        var item = switch (furnitureData.getType()) {
            case FLOOR -> createFloorObject(itemData, room, furnitureData);
            case WALL -> createWallObject(itemData, room, furnitureData);
            case null, default ->
                    throw new IllegalArgumentException(STR."Furniture type \{furnitureData.getType().toString()} is not valid object");
        };
        this.injector.injectMembers(item);

        var ownerData = habboManager.getHabboData(itemData.getOwnerId());
        ownerData.ifPresent(item::setOwnerData);
        return item;
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

        if (itemDefinitionMap.containsKey(furniture.getInteractionType())) {
            try {
                Constructor<? extends IRoomItem> constructor;

                if (itemConstructorCache.containsKey(furniture.getInteractionType())) {
                    constructor = itemConstructorCache.get(furniture.getInteractionType());
                } else {
                    constructor = itemDefinitionMap.get(furniture.getInteractionType()).getConstructor(IRoomItemData.class, IRoom.class, IFurniture.class);
                    itemConstructorCache.put(furniture.getInteractionType(), constructor);
                }

                if (constructor != null)
                    return constructor.newInstance(data, room, furniture);
            } catch (Exception e) {
                logger.warn(STR."Failed to create instance for item: \{furniture.getId()}, type: \{furniture.getInteractionType()}", e);
            }
        }

//        if (furniture.getType().equals(FurnitureType.WALL))
//            return new WallItem(data, room);
        if (furniture.getType().equals(FurnitureType.FLOOR))
            return new DefaultFloorItem(data, room, furniture);
        if (furniture.getType().equals(FurnitureType.WALL))
            return new DefaultWallItem(data, room, furniture);

        return null;
    }

    private IRoomItemData createItemDataFromResult(IConnectionResult result) throws Exception {
        var limitedEditionItemData = LimitedData.NONE;

        var limitedData = result.getString("limited_data");
        if (StringUtil.isNullOrEmpty(limitedData) || !limitedData.equals("0:0")) {
            limitedEditionItemData = LimitedData.fromString(limitedData);
        }

        final long id = result.getLong("id");
        final int itemId = result.getInt("item_id");
        final int ownerId = result.getInt("user_id");
        final int x = result.getInt("x");
        final int y = result.getInt("y");
        final double z = result.getDouble("z");
        final int rotation = result.getInt("rot");
        final String extraData = result.getString("extra_data");
        final String wallPosition = result.getString("wall_pos");

        return new RoomItemData(id, itemId, ownerId, new Position(x, y, z), rotation, extraData, wallPosition, limitedEditionItemData);

    }
}
