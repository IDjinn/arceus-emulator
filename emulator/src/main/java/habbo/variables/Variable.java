package habbo.variables;

import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

public class Variable implements IVariable {
    private final String key;
    private @Nullable String value;
    private @Nullable Instant expiresAt;
    private boolean isVisible = true;
    private boolean isPersistent;
    private @Nullable String icon;

    public Variable(String key) {
        this.key = key;
    }

    public Variable(String key, @Nullable final String value) {
        this.key = key;
        this.value = value;
    }

    public Variable(String key, @Nullable final String value, @NotNull String icon) {
        this.key = key;
        this.value = value;
        this.icon = icon;
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
    public Optional<String> getIcon() {
        return Optional.ofNullable(this.icon);
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public void setVisible(final boolean visible) {
        this.isVisible = visible;
    }

    @Override
    public Optional<Instant> expiresAt() {
        return Optional.ofNullable(this.expiresAt);
    }

    @Override
    public void setExpiresAt(final Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public boolean isPersistent() {
        return this.isPersistent;
    }

    @Override
    public void setPersistent(final boolean persistent) {
        this.isPersistent = persistent;
    }

    @Override
    public void serialize(final OutgoingPacket packet) {
        packet
                .appendString(this.getKey())
                .appendString(this.getValue())
                .appendString(this.getIcon().orElse(""))
                .appendBoolean(this.isVisible())
                .appendBoolean(this.isPersistent())
                .appendInt(this.expiresAt().isPresent() ? (int) (this.expiresAt().get().toEpochMilli() / 100L) : -1)
        ;
    }
}
