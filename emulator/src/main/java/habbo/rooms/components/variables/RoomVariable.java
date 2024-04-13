package habbo.rooms.components.variables;

import habbo.variables.Variable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RoomVariable extends Variable {
    public RoomVariable(final String key) {
        super(key);
    }

    public RoomVariable(final String key, final @Nullable String value) {
        super(key, value);
    }

    public RoomVariable(final String key, final @Nullable String value, @NotNull final String icon) {
        super(key, value, icon);
    }
}
