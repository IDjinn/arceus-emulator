package habbo.rooms.entities;

import habbo.rooms.IRoom;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.IEntityStatusComponent;
import habbo.rooms.entities.variables.IEntityVariableManager;
import networking.util.ISerializable;
import utils.cycle.ICycle;

public interface IRoomEntity extends ISerializable, ICycle {
    int getVirtualId();

    IRoom getRoom();

    String getName();

    void setName(String name);

    IEntityVariableManager getEntityVariablesManager();

    IEntityStatusComponent getStatusComponent();

    IEntityPositionComponent getPositionComponent();
}
