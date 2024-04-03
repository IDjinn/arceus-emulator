package habbo.rooms.components.objects;

import com.google.inject.Inject;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import storage.repositories.rooms.IRoomItemsRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.SequencedCollection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

public class ObjectManager implements IObjectManager {
    private final HashMap<Long, IRoomItem> items;
    private final AtomicLong virtualIdCounter;
    private final HashMap<Long, IRoomItem> itemsByVirtualId;
    private Logger logger = LogManager.getLogger();
    private IRoom room;
    @Inject
    private IRoomItemsRepository itemsRepository;
    @Inject
    private IRoomItemFactory roomItemFactory;


    public ObjectManager() {
        items = new HashMap<>();
        itemsByVirtualId = new HashMap<>();
        virtualIdCounter = new AtomicLong(1);
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
                this.items.put(item.getId(), item);
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
    public Collection<IRoomItem> getAllItems() {
        return this.items.values();
    }

    @Override
    public SequencedCollection<IRoomItem> getItemsWhere(Predicate<IRoomItem> predicate) {
        return this.items.values().stream().filter(predicate).toList();
    }

    @Override
    public long getVirtualIdForItemId(long itemId) {
        var item = this.items.get(itemId);
        assert item != null;
        
        var newId = this.virtualIdCounter.getAndIncrement();
        this.itemsByVirtualId.put(newId, item);
        return newId;
    }

    @Override
    public @Nullable IRoomItem getItemByVirtualId(long virtualId) {
        return this.itemsByVirtualId.get(virtualId);
    }
}
