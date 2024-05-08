package habbo.internationalization;

import habbo.variables.IVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class LocalizedString {
    @NotNull
    private final String key;
    @Nullable
    private final String defaultValue;

    private final List<IVariable> variables;

    public LocalizedString(@NotNull String key, @Nullable String defaultValue, List<IVariable> variables) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.variables = variables;
    }

    public static LocalizedString of(@NotNull String key, @Nullable String defaultValue, List<IVariable> variables) {
        return new LocalizedString(key, defaultValue, variables);
    }

    public static LocalizedString of(@NotNull String key, @Nullable String defaultValue) {
        return new LocalizedString(key, defaultValue, new ArrayList<>());
    }

    public static LocalizedString of(@NotNull String key, IVariable... variables) {
        return new LocalizedString(key, null, List.of(variables));
    }

    public static LocalizedString of(@NotNull String key, List<IVariable> variables) {
        return new LocalizedString(key, null, variables);
    }

    public static LocalizedString of(@NotNull String key) {
        return new LocalizedString(key, null, new ArrayList<>());
    }

    public @NotNull String getKey() {
        return this.key;
    }

    public Optional<String> getDefaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    public List<IVariable> getVariables() {
        return this.variables;
    }
}