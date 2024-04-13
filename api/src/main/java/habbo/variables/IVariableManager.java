package habbo.variables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IVariableManager {
    Map<String, IVariable> getVariables();
    
    void setOrCreate(IVariable variable);

    void setOrCreate(String variable, @Nullable String value);

    @Nullable IVariable get(@NotNull String key);

    boolean isNeedUpdate();

    void setNeedUpdate(boolean needUpdate);

    void removeVariable(@NotNull String key);

    void tick();
}
