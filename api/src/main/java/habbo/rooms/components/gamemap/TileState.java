package habbo.rooms.components.gamemap;

import java.util.HashSet;

public enum TileState {

    Open(1),

    EntityOpen(2 << 1 | Open.flags),
    FurnitureOpen(2 << 2 | Open.flags),

    Closed(1 << 1),

    EntityClosed(1 << 2 | Closed.flags),
    FurnitureClosed(1 << 3 | Closed.flags),

    ;

    static {
        final var flags = new HashSet<Integer>();
        for (TileState state : TileState.values()) {
            if (!flags.add(state.flags)) {
                throw new IllegalStateException("Illegal state flag " + state);
            }
        }
    }

    private final int flags;

    TileState(final int flags) {
        this.flags = flags;
    }

    public int getFlags() {
        return this.flags;
    }
}
