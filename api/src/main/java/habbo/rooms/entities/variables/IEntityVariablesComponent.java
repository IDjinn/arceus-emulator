package habbo.rooms.entities.variables;

import habbo.rooms.entities.IEntityComponent;
import habbo.rooms.entities.IRoomEntity;
import habbo.variables.IVariableManager;
import networking.util.ISerializable;

public interface IEntityVariablesComponent extends IVariableManager, IEntityComponent, ISerializable {
    public IRoomEntity getEntity();
}
