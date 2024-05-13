package habbo.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IVariableManager {
    Map<String, IVariable> getVariables();

    <T> IVariable<T> getOrCreate(IVariable<T> variable);

    <T> IVariable<T> getOrCreate(String variable, @Nullable String value);

    <T> IVariable<T> setOrCreate(IVariable<T> variable);

    <T> IVariable<T> setOrCreate(String variable, @Nullable String value);

    <T> @Nullable IVariable<T> get(@NotNull String key);

    boolean isNeedUpdate();

    void setNeedUpdate(boolean needUpdate);

    void removeVariable(@NotNull String key);

    void tick();

    IVariableMessageFactory getFormatter();
}
