package habbo.rooms.components.objects;

import com.google.inject.Inject;
import habbo.furniture.FurnitureType;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import habbo.rooms.components.objects.items.floor.IFloorObject;
import habbo.rooms.components.objects.items.wall.IWallItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.rooms.IRoomItemsRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class ObjectManager implements IObjectManager {
    private final HashMap<Long, IRoomItem> items;
    private final HashMap<Long, IFloorObject> floorItems;
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


    public ObjectManager() {
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
        this.logger.info("initializing room items for room = {}", this.getRoom().getId());
        this.itemsRepository.getAllRoomItems(this.getRoom().getId(), result -> {
            if (result == null) return;

            try {
                var item = this.roomItemFactory.create(result, this.getRoom());
                this.addRoomItem(item);
            } catch (Exception e) {
                logger.error(e);
            }
        });
        this.logger.info("loaded total of {} items for room = {}", this.items.size(), this.getRoom().getId());
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
                this.floorItems.put(roomItem.getId(), (IFloorObject) roomItem);
            else if (roomItem.getFurniture().getType().equals(FurnitureType.FLOOR))
                this.wallItems.put(roomItem.getId(), (IWallItem) roomItem);
        }
    }

    @Override
    public Collection<IRoomItem> getAllItems() {
        return this.items.values();
    }

    @Override
    public Collection<IFloorObject> getAllFloorItems() {
        return this.floorItems.values();
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
}
