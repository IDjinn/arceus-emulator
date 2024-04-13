package habbo.rooms.entities.components.variables;

import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.variables.IEntityVariableManager;
import habbo.variables.VariableManager;

public class EntityVariablesManager extends VariableManager implements IEntityVariableManager {
    private final IRoomEntity entity;

    public EntityVariablesManager(IRoomEntity entity) {
        this.entity = entity;
    }

    @Override
    public IRoomEntity getEntity() {
        return this.entity;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
