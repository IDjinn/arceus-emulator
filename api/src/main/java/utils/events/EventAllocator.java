package utils.events;

import habbo.rooms.components.objects.items.floor.FloorItemEvent;
import stormpot.Allocator;
import stormpot.Slot;

public class EventAllocator implements Allocator<FloorItemEvent> {

    @Override
    public FloorItemEvent allocate(Slot slot) throws Exception {
        return new FloorItemEvent(slot);
    }

    @Override
    public void deallocate(FloorItemEvent poolable) throws Exception {
    }
}
