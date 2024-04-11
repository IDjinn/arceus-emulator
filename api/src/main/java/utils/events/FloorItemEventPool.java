package utils.events;

import habbo.rooms.components.objects.items.floor.FloorItemEvent;
import stormpot.Pool;
import stormpot.Timeout;

import java.util.concurrent.TimeUnit;

public class FloorItemEventPool {
    private static final FloorItemEventPool instance = new FloorItemEventPool();

    public static FloorItemEventPool getInstance() {
        return FloorItemEventPool.instance;
    }
    
    private final Pool<FloorItemEvent> pool = Pool
            .from(new EventAllocator())
            .setSize(1024)
            .setThreadFactory(Thread.ofVirtual().factory())
            .build();

    public FloorItemEvent borrow() throws InterruptedException {
        return this.borrow(new Timeout(1, TimeUnit.SECONDS));
    }

    public FloorItemEvent borrow(Timeout timeout) throws InterruptedException {
        return this.pool.claim(timeout);
    }

    public void release(FloorItemEvent event) {
        event.release();
    }
}
