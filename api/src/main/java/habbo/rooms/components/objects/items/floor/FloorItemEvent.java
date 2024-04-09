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
        return totalTicks;
    }

    public long getTicks() {
        return ticks.get();
    }

    public void setTicks(long ticks) {
        this.ticks.set(ticks);
    }

    public void incrementTicks() {
        ticks.incrementAndGet();
    }

    public void decrementTicks() {
        ticks.decrementAndGet();
    }

    public void resetTicks() {
        ticks.set(0);
    }

    public boolean isCompleted() {
        return ticks.get() >= totalTicks;
    }


    @Override
    public void release() {
        this.setTicks(0);
        this.totalTicks = 0;
        if (this.slot != null)
            this.slot.release(this);
    }

    public IEventListener getOnCompleted() {
        return onCompleted;
    }

    public void subscribeListener(IEventListener listener) {
        this.onCompleted = listener;
    }

    public interface IEventListener {
        void onEventComplete(FloorItemEvent event);
    }
}
