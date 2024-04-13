package habbo.variables;

import core.IHotelService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.cycle.ICycle;

import java.util.Map;

public interface IVariableManager extends IHotelService, ICycle {
    Map<String, IVariable> getVariables();
    
    void setOrCreate(IVariable variable);

    void setOrCreate(String variable, @Nullable String value);

    @Nullable IVariable get(@NotNull String key);

    boolean isNeedUpdate();

    void setNeedUpdate(boolean needUpdate);

    void update();

    void removeVariable(@NotNull String key);
}
