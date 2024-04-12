package habbo.rooms.entities.variables;

import habbo.rooms.entities.IEntityComponent;
import habbo.rooms.entities.IRoomEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IEntityVariablesComponent extends IEntityComponent {
    void setOrCreate(IVariable variable);

    void setOrCreate(String variable, @Nullable String value);

    @Nullable IVariable get(@NotNull String key);


    IRoomEntity getEntity();

    boolean isNeedUpdate();

    void setNeedUpdate(boolean needUpdate);

    void update();
}
