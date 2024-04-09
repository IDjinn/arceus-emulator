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
    private Logger logger = LogManager.getLogger();

    public AdvancedFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
        events = ConcurrentHashMap.newKeySet();
    }

    public @Nullable FloorItemEvent createEvent(String name) {
        try {
            return FloorItemEventPool.getInstance().borrow();
        } catch (Exception e) {
            logger.error("failed to create event for item {} in room {}: {}", this.getId(), this.getRoom().getData().getId(), e.getMessage(), e);
            return null;
        }
    }

    public void enqueueEvent(FloorItemEvent event) {
        try {
            events.add(event);
        } catch (Exception e) {
            logger.error("failed to enqueue event for item {} in room {}: {}", this.getId(), this.getRoom().getData().getId(), e.getMessage(), e);
        }
    }

    @Override
    public synchronized void tick() {
        try {
            var i = 0;
            final var iterator = events.iterator();
            while (i++ < MAX_EVENT_COUNT && iterator.hasNext()) {
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
            logger.error("failed while processing item {} events in room {}: {}", this.getId(), this.getRoom().getData().getId(), e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        for (FloorItemEvent event : events) {
            FloorItemEventPool.getInstance().release(event);
        }
    }
}