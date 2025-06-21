package habbo.rooms.components.variables;

import habbo.variables.Variable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RoomVariable<T> extends Variable<T> {
    public RoomVariable(final String key) {
        super(key);
    }

    public RoomVariable(final String key, final @Nullable T value) {
        super(key, value);
    }

    public RoomVariable(final String key, final @Nullable T value, @NotNull final String icon) {
        super(key, value, icon);
    }
}
