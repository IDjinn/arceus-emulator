package habbo.rooms.components.objects.items.floor;

import org.jetbrains.annotations.Nullable;
import stormpot.Poolable;
import stormpot.Slot;

import java.util.concurrent.atomic.AtomicLong;

public class FloorItemEvent implements Poolable {
    public static final FloorItemEvent Never = new FloorItemEvent(null);

    private final AtomicLong ticks = new AtomicLong(0);
    private @Nullable
    final Slot slot;
    private long totalTicks;
    private IEventListener onCompleted;

    public FloorItemEvent(Slot slot) {
        this.slot = slot;
    }

    public long getTotalTicks() {
        return this.totalTicks;
    }

    public long getTicks() {
        return this.ticks.get();
    }

    public void setTicks(long ticks) {
        this.ticks.set(ticks);
    }

    public void incrementTicks() {
        this.ticks.incrementAndGet();
    }

    public void decrementTicks() {
        this.ticks.decrementAndGet();
    }

    public void resetTicks() {
        this.ticks.set(0);
    }

    public boolean isCompleted() {
        return this.ticks.get() >= this.totalTicks;
    }

    public void setTotalTicks(long totalTicks) {
        this.totalTicks = totalTicks;
    }


    @Override
    public void release() {
        this.setTicks(0);
        this.totalTicks = 0;
        if (this.slot != null)
            this.slot.release(this);
    }

    public IEventListener getOnCompleted() {
        return this.onCompleted;
    }

    public void subscribeListener(IEventListener listener) {
        this.onCompleted = listener;
    }

    public interface IEventListener {
        void onEventComplete(FloorItemEvent event);
    }
}
