package habbo.rooms.components.objects.items.floor;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import utils.cycle.ICycle;
import utils.events.FloorItemEventPool;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class AdvancedFloorItem extends DefaultFloorItem implements ICycle {
    private final Set<FloorItemEvent> events;
    private final int MAX_EVENT_COUNT = 1000; // prevent infinite recursion in while loop
    private final Logger logger = LogManager.getLogger();

    public AdvancedFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
        this.events = ConcurrentHashMap.newKeySet();
    }

    public @Nullable FloorItemEvent createEvent(String name) {
        try {
            return FloorItemEventPool.getInstance().borrow();
        } catch (Exception e) {
            this.logger.error("failed to create event for item {} in room {}: {}", this.getId(), this.getRoom().getData().getId(), e.getMessage(), e);
            return null;
        }
    }

    public void enqueueEvent(FloorItemEvent event) {
        try {
            if (this.events.size() + 1 < this.MAX_EVENT_COUNT)
                this.events.add(event);
            else
                this.logger.warn("failed to enqueue event for item {} in room {}: too many events", this.getId(),
                        this.getRoom().getData().getId());
        } catch (Exception e) {
            this.logger.error("failed to enqueue event for item {} in room {}: {}", this.getId(), this.getRoom().getData().getId(), e.getMessage(), e);
        }
    }

    @Override
    public synchronized void tick() {
        try {
            var i = 0;
            final var iterator = this.events.iterator();
            while (i++ < this.MAX_EVENT_COUNT && iterator.hasNext()) {
                final var event = iterator.next();
                event.incrementTicks();

                if (event.isCompleted()) {
                    try {
                        iterator.remove();
                        event.getOnCompleted().onEventComplete(event);
                    } finally {
                        event.release();
                    }
                }
            }
        } catch (Exception e) {
            this.logger.error("failed while processing item {} events in room {}: {}", this.getId(), this.getRoom().getData().getId(), e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        for (FloorItemEvent event : this.events) {
            FloorItemEventPool.getInstance().release(event);
        }
    }
}