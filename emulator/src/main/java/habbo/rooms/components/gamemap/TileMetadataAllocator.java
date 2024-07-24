package habbo.rooms.components.gamemap;

import stormpot.Allocator;
import stormpot.Slot;

public class TileMetadataAllocator implements Allocator<TileMetadata> {
    @Override
    public TileMetadata allocate(final Slot slot) throws Exception {
        return new TileMetadata(slot);
    }

    @Override
    public void deallocate(final TileMetadata poolable) throws Exception {

    }
}
