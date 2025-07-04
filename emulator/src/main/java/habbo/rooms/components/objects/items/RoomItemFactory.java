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
import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import habbo.rooms.components.objects.items.floor.interactions.LayFloorItem;
import habbo.rooms.components.objects.items.floor.interactions.RollerFloorItem;
import habbo.rooms.components.objects.items.floor.interactions.SitFloorItem;
import habbo.rooms.components.objects.items.wall.DefaultWallItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import storage.results.IConnectionResult;
import utils.pathfinder.Position;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RoomItemFactory implements IRoomItemFactory {
    private final Injector injector;
    private final IFurnitureManager furnitureManager;
    private final IHabboManager habboManager;
    private static final Logger logger = LogManager.getLogger(RoomItemFactory.class);

    public final Map<String, Class<? extends IRoomItem>> itemDefinitionMap;
    public final Map<String, Constructor<? extends IRoomItem>> itemConstructorCache;

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
        logger.info("RoomItemFactory initialized with total of {} interactions", this.itemDefinitionMap.size());
    }

    @Override
    public boolean registerInteraction(final @NotNull String name, final Class<? extends IRoomItem> clazz) {
        if (this.itemDefinitionMap.containsKey(name)) return false;
        this.itemDefinitionMap.put(name, clazz);
        return true;
    }

    @Override
    public boolean unregisterInteraction(final @NotNull String name) {
        return this.itemDefinitionMap.remove(name) != null;
    }

    @Override
    public IRoomItem create(IConnectionResult result, IRoom room) throws Exception {
        var itemData = this.createItemDataFromResult(result);
        return this.create(itemData, room);
    }

    @Override
    public IRoomItem create(IRoomItemData itemData, IRoom room) {
        var furnitureData = this.furnitureManager.get(itemData.getFurnitureId());
        if (furnitureData == null) {
            throw new IllegalArgumentException("Furniture data not found for id " + itemData.getFurnitureId());
        }

        IRoomItem item = switch (furnitureData.getType()) {
            case FLOOR -> this.createFloorObject(itemData, room, furnitureData);
            case WALL -> this.createWallObject(itemData, room, furnitureData);
            default ->
                    throw new IllegalArgumentException("Furniture type " + furnitureData.getType() + " is not a valid object");
        };

        this.injector.injectMembers(item);

        this.habboManager.getHabboData(itemData.getOwnerId()).ifPresent(item::setOwnerData);

        return item;
    }

    private IFloorFloorItem createFloorObject(IRoomItemData data, IRoom room, IFurniture furnitureData) {
        IRoomItem object = this.createRoomObject(data, room, furnitureData);
        if (object == null) {
            throw new IllegalArgumentException("Furniture type " + furnitureData.getId() + " couldn't be created with interaction type " + furnitureData.getInteractionType() + ". No constructors were found.");
        }
        return (IFloorFloorItem) object;
    }

    private IWallItem createWallObject(IRoomItemData data, IRoom room, IFurniture furnitureData) {
        IRoomItem object = this.createRoomObject(data, room, furnitureData);
        if (object == null) {
            throw new IllegalArgumentException("Furniture type " + furnitureData.getId() + " couldn't be created with interaction type " + furnitureData.getInteractionType() + ". No constructors were found.");
        }
        return (IWallItem) object;
    }

    private @Nullable IRoomItem createRoomObject(IRoomItemData data, IRoom room, IFurniture furniture) {
        assert furniture != null;
        assert room != null;

        if (this.itemDefinitionMap.containsKey(furniture.getInteractionType())) {
            try {
                Constructor<? extends IRoomItem> constructor = this.itemConstructorCache.computeIfAbsent(furniture.getInteractionType(), key -> {
                    try {
                        return this.itemDefinitionMap.get(key).getConstructor(IRoomItemData.class, IRoom.class, IFurniture.class);
                    } catch (NoSuchMethodException e) {
                        return null;
                    }
                });

                if (constructor != null) {
                    return constructor.newInstance(data, room, furniture);
                }
            } catch (Exception e) {
                logger.warn("Failed to create instance for item: {}, type: {}", furniture.getId(), furniture.getInteractionType(), e);
            }
        }

        logger.warn("Interaction type {} was not found to create instance for item {}", furniture.getInteractionType(), data.getId());

        if (furniture.isCanSit()) {
            return new SitFloorItem(data, room, furniture);
        }
        if (furniture.isCanLay()) {
            return new LayFloorItem(data, room, furniture);
        }
        if (furniture.getType() == FurnitureType.FLOOR) {
            return new DefaultFloorItem(data, room, furniture);
        }
        if (furniture.getType() == FurnitureType.WALL) {
            return new DefaultWallItem(data, room, furniture);
        }

        return null;
    }

    private IRoomItemData createItemDataFromResult(IConnectionResult result) throws Exception {
        final var id = result.getInt("id");
        final var itemId = result.getInt("item_id");
        final var ownerId = result.getInt("user_id");
        final var x = result.getInt("x");
        final var y = result.getInt("y");
        final var z = result.getDouble("z");
        final var rotation = result.getInt("rot");
        final var wallPosition = result.getString("wall_pos");
        final var wiredData = result.getString("wired_data");

        final IExtraData extraData = this.furnitureManager.parseExtraData(result.getString("extra_data"));
        extraData.setLimitedData(LimitedData.fromString(result.getString("limited_data")));

        return new RoomItemData(id, itemId, ownerId, new Position(x, y, z), rotation, extraData, wallPosition, wiredData);
    }

    @Override
    public IRoomItemData createItemData(final int id, final int furnitureId, final int ownerId, final Position position, final int rotation, final IExtraData extraData, final String wiredData) {
        return new RoomItemData(id, furnitureId, ownerId, position, rotation, extraData, "", wiredData);
    }

    @Override
    public IRoomItemData createItemData(int id, int furnitureId, int ownerId, String wallPosition, IExtraData extraData) {
        return new RoomItemData(id, furnitureId, ownerId, new Position(-1, -1, 0d), 0, extraData, wallPosition, "");
    }
}