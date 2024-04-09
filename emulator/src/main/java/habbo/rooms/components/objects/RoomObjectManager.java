package habbo.rooms.components.objects;

import com.google.inject.Inject;
import habbo.furniture.FurnitureType;
import habbo.habbos.inventory.IHabboInventoryItem;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.wall.IWallItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import packets.outgoing.rooms.objects.AddFloorItemComposer;
import packets.outgoing.rooms.objects.AddWallItemComposer;
import storage.repositories.rooms.IRoomItemsRepository;
import utils.Position;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class RoomObjectManager implements IRoomObjectManager {
    private final HashMap<Long, IRoomItem> items;
    private final HashMap<Long, IFloorItem> floorItems;
    private final HashMap<Long, IWallItem> wallItems;
    private final AtomicInteger virtualIdCounter;
    private final HashMap<Integer, IRoomItem> itemsByVirtualId;
    private Logger logger = LogManager.getLogger();
    private IRoom room;
    @Inject
    private IRoomItemsRepository itemsRepository;
    @Inject
    private IRoomItemFactory roomItemFactory;

    private final HashSet<String> furnitureOwners;


    public RoomObjectManager() {
        items = new HashMap<>();
        itemsByVirtualId = new HashMap<>();
        virtualIdCounter = new AtomicInteger(1);
        furnitureOwners = new HashSet<>(1);
        wallItems = new HashMap<>();
        floorItems = new HashMap<>();
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
                logger.error(e);
            }
        });
        this.logger.info("loaded total of {} items for room = {}", this.items.size(), this.getRoom().getData().getId());
    }

    @Override
    public void onRoomLoaded() {
        this.items.values().forEach(IRoomObject::onRoomLoaded);
    }

    @Override
    public void destroy() {

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

    @Override
    public Collection<IFloorItem> getAllFloorItemsAt(final Position position) {
        return this.getAllFloorItemsAt(position, -1);
    }

    @Override
    public Collection<IFloorItem> getAllFloorItemsAt(final Position position, int ignoreId) { // TODO POOLING
        final var itemsAt = new HashSet<IFloorItem>();
        for (var item : this.floorItems.values()) {
            if (item.getId() == ignoreId) continue;
            if (!item.getPosition().equals(position)) continue;

            itemsAt.add(item);
        }

        return itemsAt;
    }

    @Override
    public Optional<IFloorItem> getTopFloorItemAt(final Position position, final int ignoreId) {
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

    @Override
    public int getVirtualIdForItemId(long itemId) {
        var item = this.items.get(itemId);
        assert item != null;
        
        var newId = this.virtualIdCounter.getAndIncrement();
        this.itemsByVirtualId.put(newId, item);
        return newId;
    }

    @Override
    public @Nullable IRoomItem getItemByVirtualId(int virtualId) {
        return this.itemsByVirtualId.get(virtualId);
    }

    public List<String> getFurnitureOwners() {
        return furnitureOwners.stream().toList();
    }

    @Override
    public void placeWallItem(IHabboInventoryItem item, String wallPosition) {
        try {
            itemsRepository.placeWallItemFromInventory(result -> {
                var itemData = roomItemFactory.createItemData(
                        item.getId(),
                        item.getFurniture().getId(),
                        item.getHabbo().getData().getId(),
                        wallPosition, // TODO VALIDATION
                        item.getExtraData()
                );
                var wallItem = (IWallItem) this.roomItemFactory.create(itemData, this.getRoom());
                this.addRoomItem(wallItem);

                this.getRoom().broadcastMessage(new AddWallItemComposer((IWallItem) wallItem));
            }, this.getRoom().getData().getId(), item.getId(), wallPosition);
        } catch (Exception e) {
            logger.error("failed to place item {} from inventory to floor", item.getId(), e);
        }
    }

    @Override
    public void placeFloorItem(IHabboInventoryItem item, int x, int y, double z, int rotation) {
        try {
            itemsRepository.placeFloorItemFromInventory(result -> {
                var itemData = roomItemFactory.createItemData(
                        item.getId(),
                        item.getFurniture().getId(),
                        item.getHabbo().getData().getId(),
                        new Position(x, y, z),
                        rotation,
                        item.getExtraData()
                );
                var floorItem = (IFloorItem) this.roomItemFactory.create(itemData, this.getRoom());
                this.addRoomItem(floorItem);

                this.getRoom().broadcastMessage(new AddFloorItemComposer((IFloorItem) floorItem));
            }, this.getRoom().getData().getId(), item.getId(), x, y, z, rotation);
        } catch (Exception e) {
            logger.error("failed to place item {} from inventory to floor", item.getId(), e);
        }
    }
}
