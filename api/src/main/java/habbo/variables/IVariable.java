package habbo.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

public interface IVariable<T> {
    @NotNull String getKey();

    T getValue();

    T getValue(T defaultValue);

    void setValue(T value);

    Optional<String> getIcon();

    Optional<Instant> expiresAt();

    void setExpiresAt(@Nullable Instant instant);

    boolean isEditable();

    void setEditable(boolean editable);

    boolean isVisible();

    void setVisible(boolean visible);

    boolean isPersistent();

    void setPersistent(boolean persistent);
}
