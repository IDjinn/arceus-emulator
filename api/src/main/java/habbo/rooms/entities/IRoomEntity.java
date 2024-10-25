package habbo.rooms.entities;

import core.events.IEventHandler;
import habbo.rooms.IRoom;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.IEntityStatusComponent;
import habbo.rooms.entities.variables.IEntityVariablesComponent;
import networking.util.ISerializable;
import utils.cycle.ICycle;

import java.util.Map;
import java.util.Optional;

public interface IRoomEntity extends ISerializable, ICycle {
    int getVirtualId();

    void init();

    default void onLoaded() {

    }

    default void destroy() {

    }

    IRoom getRoom();

    String getName();

    void setName(String name);
    
    IEventHandler getEventHandler();
}
