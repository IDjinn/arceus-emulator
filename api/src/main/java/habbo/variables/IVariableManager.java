package habbo.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IVariableManager {
    Map<String, IVariable<?>> getVariables();

    <T> IVariable<T> getOrCreate(final IVariable<T> variable);

    <T> IVariable<T> setOrCreate(final IVariable<T> variable);

    <T> IVariable<T> setOrCreate(final String key, final T value);

    <T> @Nullable IVariable<T> get(final @NotNull String key);

    <T> @Nullable IVariable<T> get(final @NotNull String key, Class<T> valueType);

    <T> IVariable<T> set(final IVariable<T> variable);

    boolean isNeedUpdate();

    void setNeedUpdate(boolean needUpdate);

    void deleteVariable(@NotNull String key);

    void tick();

    IVariableMessageFactory getFormatter();
}
