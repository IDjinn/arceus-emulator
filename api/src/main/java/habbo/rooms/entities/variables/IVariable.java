package habbo.rooms.entities.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

public interface IVariable {
    public @NotNull String getKey();

    public @Nullable String getValue();

    public void setValue(@Nullable String value);

    public @Nullable Duration expiresAt();

    public boolean isVisible();

    public void setVisible(boolean visible);
}
