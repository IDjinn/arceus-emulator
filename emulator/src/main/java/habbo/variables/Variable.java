package habbo.variables;

import networking.packets.outgoing.IOutgoingDTOSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Optional;

public class Variable<T> implements IVariable<T> {
    private final String key;
    private T value;
    private @Nullable Instant expiresAt;
    private boolean isVisible = true;
    private boolean isPersistent;
    private @Nullable String icon;
    private boolean editable;

    public Variable(String key) {
        this.key = key;
    }

    public Variable(String key, final T value) {
        this.key = key;
        this.value = value;
    }

    public Variable(String key, final T value, @NotNull String icon) {
        this.key = key;
        this.value = value;
        this.icon = icon;
    }

    @Override
    public boolean isEditable() {
        return this.editable;
    }

    @Override
    public void setEditable(final boolean editable) {
        this.editable = editable;
    }
    
    @Override
    public @NotNull String getKey() {
        return this.key;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public T getValue(final T defaultValue) {
        return this.value != null ? this.value : defaultValue;
    }

    @Override
    public void setValue(final T value) {
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
    public void serialize(final IOutgoingDTOSerializer<U> packet) {
        packet
                .appendString(this.getKey())
                .appendString(String.valueOf(this.getValue()))
                .appendString(this.getIcon().orElse(""))
                .appendBoolean(this.isVisible())
                .appendBoolean(this.isEditable())
                .appendBoolean(this.isPersistent())
                .appendInt(this.expiresAt().isPresent() ? (int) (this.expiresAt().get().toEpochMilli() / 100L) : -1)
        ;
    }
}
