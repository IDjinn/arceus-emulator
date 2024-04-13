package habbo.variables;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

public interface IVariable {
    @NotNull String getKey();

    @Nullable String getValue();

    void setValue(@Nullable String value);

    Optional<String> getIcon();

    Optional<Instant> expiresAt();

    void setExpiresAt(@Nullable Instant instant);

    boolean isVisible();

    void setVisible(boolean visible);

    boolean isPersistent();

    void setPersistent(boolean persistent);

    void serialize(OutgoingPacket packet);
}
