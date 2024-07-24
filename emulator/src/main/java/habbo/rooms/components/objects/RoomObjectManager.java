package habbo.rooms.components.objects;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import habbo.GameConstants;
import habbo.furniture.FurnitureType;
import habbo.habbos.IHabbo;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import packets.outgoing.inventory.AddHabboItemCategory;
import packets.outgoing.inventory.AddHabboItemComposer;
import packets.outgoing.inventory.RemoveHabboItemComposer;
import packets.outgoing.rooms.gamemap.RoomRelativeMapComposer;
import packets.outgoing.rooms.objects.floor.AddFloorItemComposer;
import packets.outgoing.rooms.objects.floor.MoveOrRotateFloorItemComposer;
import packets.outgoing.rooms.objects.floor.RemoveFloorItemComposer;
import packets.outgoing.rooms.objects.wall.AddWallItemComposer;
import packets.outgoing.rooms.objects.wall.RemoveWallItemComposer;
import packets.outgoing.rooms.objects.wall.WallItemUpdateComposer;
import storage.repositories.habbo.IHabboInventoryRepository;
import storage.repositories.rooms.IRoomItemsRepository;
import utils.cycle.ICycle;
import utils.pathfinder.Position;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class RoomObjectManager implements IRoomObjectManager {
    private final HashMap<Integer, IRoomItem> items;
    private final HashMap<Integer, IFloorItem> floorItems;
    private final HashMap<Integer, IWallItem> wallItems;
    private final Logger logger = LogManager.getLogger();
    private IRoom room;
    @Inject
    private IRoomItemsRepository itemsRepository;
    @Inject
    private IRoomItemFactory roomItemFactory;
    @Inject
    private IHabboInventoryRepository inventoryRepository;

    private final HashSet<String> furnitureOwners;


    public RoomObjectManager() {
        this.items = new HashMap<>();
        this.furnitureOwners = new HashSet<>(1);
        this.wallItems = new HashMap<>();
        this.floorItems = new HashMap<>();
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(IRoom room) {
        this.room = room;
        this.logger.info("initializing room items for room = {}", this.getRoom().getData().getId());
        this.itemsRepository.getAllRoomItems(this.getRoom().getData().getId(), result -> {
            if (result == null) return;

            try {
                var item = this.roomItemFactory.create(result, this.getRoom());
                this.addRoomItem(item);
            } catch (Exception e) {
                this.logger.error(e);
            }
        });

        this.getRoom().getGameMap().updateTiles();
        this.logger.info("loaded total of {} items for room = {}", this.items.size(), this.getRoom().getData().getId());
    }

    @Override
    public void onRoomLoaded() {
        this.items.values().forEach(IRoomObject::onRoomLoaded);
        this.getRoom().getProcessHandler().registerProcess(RoomObjectManager.class.getSimpleName(), this::tick,
                ICycle.DEFAULT_CYCLE_INTERVAL_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    @Override
    public void update() {
        this.itemsRepository.updateItemsBatch(statement -> {
            for (var item : this.items.values()) {
                if (!item.needSave()) continue;

                if (item instanceof IFloorItem floorItem) {
                } else if (item instanceof IWallItem wallItem) {
                    statement.setInt(1, wallItem.getRoom().getData().getId());
                    statement.setInt(2, 0);
                    statement.setInt(3, 0);
                    statement.setDouble(4, 0);
                    statement.setInt(5, 0);
                    statement.setString(6, wallItem.getWallPosition());
                } else {
                    throw new IllegalArgumentException("invalid item type");
                }

                statement.setString(7, item.getExtraData().toJson());
                statement.setString(8, item.getExtraData().getLimitedData().toString());
                statement.setInt(9, item.getItemData().getOwnerId());
                statement.setInt(10, item.getGroup());
                statement.setInt(11, item.getItemData().getId());
                statement.addBatch();
            }
        }, result -> {
        });
    }

    @Override
    public void destroy() {
        this.items.values().forEach(IRoomObject::destroy);
    }

    @Override
    public void addRoomItem(IRoomItem roomItem) {
        synchronized (this.items) {
            this.items.put(roomItem.getId(), roomItem);
            if (roomItem.getFurniture().getType().equals(FurnitureType.FLOOR))
                this.floorItems.put(roomItem.getId(), (IFloorItem) roomItem);
            else if (roomItem.getFurniture().getType().equals(FurnitureType.WALL))
                this.wallItems.put(roomItem.getId(), (IWallItem) roomItem);
        }
    }

    @Override
    public Collection<IRoomItem> getAllItems() {
        return this.items.values();
    }

    @Override
    public Collection<IFloorItem> getAllFloorItems() {
        return this.floorItems.values();
    }

    @Nullable
    @Override
    public IFloorItem getFloorItem(final int itemId) {
        final var realId = itemId & ~GameConstants.FurnitureVirtualIdMask;
        return this.floorItems.get(realId);
    }

    @Nullable
    @Override
    public IRoomItem getItem(final int itemId) {
        final var realId = itemId & ~GameConstants.FurnitureVirtualIdMask; // TODO MOVE IT TO PACKET ASSERTION
        return this.items.get(realId);
    }

    @Override
    public List<IFloorItem> getAllFloorItemsSortedAt(final Position position) {
        return this.getAllFloorItemsSortedAt(position, -1);
    }

    @Override
    public List<IFloorItem> getAllFloorItemsSortedAt(final Position position, int ignoreId) { // TODO POOLING
        final var itemsAt = new HashSet<IFloorItem>();
        final var realId = ignoreId & ~GameConstants.FurnitureVirtualIdMask;
        for (var item : this.floorItems.values()) {
            if (item.getId() == realId) continue;
            if (!item.getPosition().equals(position)) continue;

            itemsAt.add(item);
        }

        return itemsAt.stream().sorted().toList();
    }

    @Override
    public Optional<IFloorItem> getTopFloorItemAt(final Position position, final long ignoreId) {
        return this.floorItems.values().stream().filter(i -> i.getPosition().equals(position)).filter(i -> i.getId() != ignoreId).findFirst();
    }

    @Override
    public Collection<IWallItem> getAllWallItems() {
        return this.wallItems.values();
    }

    @Override
    public SequencedCollection<IRoomItem> getItemsWhere(Predicate<IRoomItem> predicate) {
        return this.items.values().stream().filter(predicate).toList();
    }

    public List<String> getFurnitureOwners() {
        return this.furnitureOwners.stream().toList();
    }

    @Override
    public void placeFloorItem(final IHabbo habbo, IHabboInventoryItem item, int x, int y, double z, int rotation) {
        try {
            var targetPosition = new Position(x, y, z);
            if (!this.getRoom().getGameMap().isValidCoordinate(targetPosition))
                return; // TODO ROOM ITEM PLACEMENT ERROR

            // TODO RANGE TILES & ROTATIONS

            final var tile = this.getRoom().getGameMap().getTile(targetPosition);
            var topItem = this.getTopFloorItemAt(targetPosition, -1);
            if (topItem.isPresent()) {
                if (!topItem.get().canStack(item.getHabbo().getPlayerEntity()))
                    return;
            }

            final var stackHeight =
                    topItem.isEmpty() ? tile.getZ() :
                            topItem.get().getStackHeight(item.getHabbo().getPlayerEntity()).get();
            targetPosition.setZ(stackHeight);
            this.itemsRepository.placeFloorItemFromInventory(result -> {
                var itemData = this.roomItemFactory.createItemData(
                        item.getId(),
                        item.getFurniture().getId(),
                        item.getHabbo().getData().getId(),
                        targetPosition,
                        rotation,
                        item.getExtraData(),
                        ""
                );
                var floorItem = (IFloorItem) this.roomItemFactory.create(itemData, this.getRoom());
                this.addRoomItem(floorItem);
                habbo.getInventory().removeItem(floorItem.getId());
                habbo.getClient().sendMessage(new RemoveHabboItemComposer(floorItem.getId()));

                this.getRoom().getGameMap().updateTile(tile);
                this.getRoom().getGameMap().sendUpdate(tile);
                this.getRoom().broadcastMessage(new AddFloorItemComposer(floorItem));
                topItem.ifPresent(iFloorItem -> iFloorItem.onStackInItem(floorItem));
                this.sendRelativeMap();
            }, this.getRoom().getData().getId(), item.getId(), targetPosition.getX(), targetPosition.getY(), targetPosition.getZ(), rotation);
        } catch (Exception e) {
            this.logger.error("failed to place item {} from inventory to floor", item.getId(), e);
        }
    }

    @Override
    public void placeWallItem(final IHabbo habbo, IHabboInventoryItem item, String wallPosition) {
        try {
            this.itemsRepository.placeWallItemFromInventory(result -> {
                var itemData = this.roomItemFactory.createItemData(
                        item.getId(),
                        item.getFurniture().getId(),
                        item.getHabbo().getData().getId(),
                        wallPosition, // TODO VALIDATION
                        item.getExtraData()
                );
                var wallItem = (IWallItem) this.roomItemFactory.create(itemData, this.getRoom());
                this.addRoomItem(wallItem);
                habbo.getInventory().removeItem(wallItem.getId());

                this.getRoom().broadcastMessage(new AddWallItemComposer(wallItem));
                habbo.getClient().sendMessage(new RemoveHabboItemComposer(wallItem.getId()));
            }, this.getRoom().getData().getId(), item.getId(), wallPosition);
        } catch (Exception e) {
            this.logger.error("failed to place item {} from inventory to floor", item.getId(), e);
        }
    }

    @Override
    public void moveFloorItemTo(final IHabbo habbo, final IFloorItem item, final Position position, final Integer rotation) {
        if (!this.getRoom().getGameMap().isValidCoordinate(position)) return;

        final var oldPosition = position.copy();
        final var oldTile = this.getRoom().getGameMap().getTile(oldPosition);
        
        item.setPosition(position);
        item.setRotation(rotation);
        item.onMove(oldPosition);

        final var newTile = this.getRoom().getGameMap().getTile(position);
        this.getRoom().getGameMap().updateTiles(oldTile, newTile);
        this.getRoom().getGameMap().sendUpdate(oldTile, newTile);
        this.getRoom().broadcastMessage(new MoveOrRotateFloorItemComposer(item));
        this.sendRelativeMap();
    }

    @Override
    public void moveWallItemTo(final IHabbo habbo, final IWallItem wallItem, final String coordinates) {
        wallItem.setWallPosition(coordinates);
        this.getRoom().broadcastMessage(new WallItemUpdateComposer(wallItem));
    }

    @Override
    public void pickupItem(final IHabbo habbo, final IRoomItem item) {
        this.inventoryRepository.pickupItem(result -> {
            item.onRemove(habbo);
            if (item instanceof IFloorItem floorItem) {
                final var tile = this.getRoom().getGameMap().getTile(floorItem.getPosition());
                this.getRoom().getGameMap().updateTile(tile);
                this.getRoom().getGameMap().sendUpdate(tile);
                this.getRoom().broadcastMessage(new RemoveFloorItemComposer(floorItem, habbo.getData().getId()));
            } else if (item instanceof IWallItem wallItem) {
                this.getRoom().broadcastMessage(new RemoveWallItemComposer(wallItem, habbo.getData().getId()));
            }

            this.sendRelativeMap();
            habbo.getInventory().removeItem(item.getId());
            habbo.getInventory().sendUpdate();
            if (item.getOwnerData().isPresent()) {
                if (item.getOwnerData().get().getId() == habbo.getData().getId()) {
                    habbo.getClient().sendMessage(new AddHabboItemComposer(AddHabboItemCategory.OwnedFurni, Lists.newArrayList(item.getVirtualId())));
                }
            }
        }, item.getId(), habbo.getData().getId());
    }

    @Override
    public synchronized void tick() {
        for (var item : this.items.values()) {
            try {
                if (item instanceof ICycle statefulItem)
                    statefulItem.tick();
            } catch (Exception e) {
                this.logger.error(e);
            }
        }
    }

    @Override
    public void sendRelativeMap() {
        this.getRoom().broadcastMessage(new RoomRelativeMapComposer(this.getRoom().getGameMap()));
    }
}
