package habbo.internationalization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class LocalizedString {
    @NotNull
    private final String key;
    @Nullable
    private final String defaultValue;

    public LocalizedString(@NotNull String key, @Nullable String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public @NotNull String getKey() {
        return key;
    }

    public static LocalizedString of(@NotNull String key, @Nullable String defaultValue) {
        return new LocalizedString(key, defaultValue);
    }

    public static LocalizedString of(@NotNull String key) {
        return new LocalizedString(key, null);
    }

    public Optional<String> getDefaultValue() {
        return Optional.ofNullable(defaultValue);
    }
}