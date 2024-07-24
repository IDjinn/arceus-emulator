package habbo.rooms.entities;

import core.events.IEventHandler;
import habbo.rooms.IRoom;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.IEntityStatusComponent;
import habbo.rooms.entities.variables.IEntityVariablesComponent;
import utils.cycle.ICycle;

import java.util.Map;
import java.util.Optional;

public interface IRoomEntity extends ICycle {
    int getVirtualId();

    void init();

    default void onLoaded() {

    }

    default void destroy() {

    }

    IRoom getRoom();

    String getName();

    void setName(String name);

    Map<Class<? extends IEntityComponent>, IEntityComponent> getEntityComponents();

    void registerEntityComponent(Class<? extends IEntityComponent> componentClass, IEntityComponent component);

    <T extends IEntityComponent> void registerEntityComponent(IEntityComponent component);

    void unregisterEntityComponent(Class<? extends IEntityComponent> componentClass);

    <T extends IEntityComponent> T getEntityComponent(Class<? extends IEntityComponent> component);
    
    <T extends IEntityComponent> Optional<T> getSafeEntityComponent(Class<? extends IEntityComponent> component);
    
    IEventHandler getEventHandler();

    IEntityVariablesComponent getEntityVariablesComponent();

    IEntityStatusComponent getStatusComponent();

    IEntityPositionComponent getPositionComponent();
}
