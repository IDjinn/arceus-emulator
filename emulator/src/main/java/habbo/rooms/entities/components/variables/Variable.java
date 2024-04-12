package habbo.rooms.entities.components.variables;

import habbo.rooms.entities.variables.IVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

public class Variable implements IVariable {
    private final String key;
    private @Nullable String value;
    private @Nullable Duration expiresAt;
    private boolean isVisible;

    public Variable(String key) {
        this.key = key;
    }

    public Variable(String key, @Nullable final String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public @NotNull String getKey() {
        return this.key;
    }

    @Override
    public @Nullable String getValue() {
        return this.value;
    }

    @Override
    public void setValue(@Nullable final String value) {
        this.value = value;
    }

    @Override
    public @Nullable Duration expiresAt() {
        return this.expiresAt;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public void setVisible(final boolean visible) {
        this.isVisible = visible;
    }
}
