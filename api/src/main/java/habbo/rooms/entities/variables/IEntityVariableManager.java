package habbo.rooms.entities.variables;

import habbo.rooms.entities.IRoomEntity;
import habbo.variables.IVariableManager;

public interface IEntityVariableManager extends IVariableManager {
    public IRoomEntity getEntity();
}
