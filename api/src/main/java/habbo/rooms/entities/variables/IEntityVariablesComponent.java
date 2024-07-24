package habbo.rooms.entities.variables;

import habbo.rooms.entities.IEntityComponent;
import habbo.rooms.entities.IRoomEntity;
import habbo.variables.IVariableManager;

public interface IEntityVariablesComponent extends IVariableManager, IEntityComponent {
    IRoomEntity getEntity();
}
