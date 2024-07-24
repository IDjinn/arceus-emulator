package habbo.rooms.entities;

import com.google.inject.Inject;
import core.events.IEventHandler;
import habbo.rooms.IRoom;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.IEntityStatusComponent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RoomEntity implements IRoomEntity {
    private final int virtualId;
    private String name;
    private final IRoom room;

    private @Inject IEntityStatusComponent statusComponent;
    private @Inject IEntityPositionComponent positionComponent;
    private @Inject IEventHandler eventHandler;

    private final ConcurrentHashMap<Class<? extends IEntityComponent>, IEntityComponent> components;

    public RoomEntity(IRoom room, int virtualId) {
        this.room = room;
        this.virtualId = virtualId;
        this.components = new ConcurrentHashMap<>();
    }

    @Override
    public int getVirtualId() {
        return this.virtualId;
    }

    @Override
    public void init() {
        assert this.statusComponent != null;
        assert this.positionComponent != null;
        assert this.eventHandler != null;

        this.components.put(this.statusComponent.getClass(), this.statusComponent);
        this.components.put(this.positionComponent.getClass(), this.positionComponent);

        for (final var component : this.components.values()) {
            component.init(this);
        }
    }

    @Override
    public void destroy() {
        for (final var component : this.components.values()) {
            component.destroy();
        }
    }

    @Override
    public Map<Class<? extends IEntityComponent>, IEntityComponent> getEntityComponents() {
        return this.components;
    }

    @Override
    public void registerEntityComponent(final Class<? extends IEntityComponent> componentClass, final IEntityComponent component) {
        assert componentClass != null;
        assert component != null;
        assert componentClass.isInstance(component);
        this.components.put(componentClass, component);
        component.init(this);
    }

    @Override
    public <T extends IEntityComponent> void registerEntityComponent(final IEntityComponent component) {
        assert component != null;
        this.components.put(component.getClass(), component);
        component.init(this);
    }

    @Override
    public void unregisterEntityComponent(final Class<? extends IEntityComponent> componentClass) {
        assert componentClass != null;
        final var component = this.components.remove(componentClass);
        if (component != null)
            component.destroy();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IEntityComponent> T getEntityComponent(final Class<? extends IEntityComponent> component) {
        return (T) this.components.get(component);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IEntityComponent> Optional<T> getSafeEntityComponent(final Class<? extends IEntityComponent> component) {
        if (!this.components.containsKey(component)) return Optional.empty();

        final var instance = this.components.get(component);
        if (instance == null || !instance.getClass().isInstance(component))
            return Optional.empty();

        return Optional.of((T) instance);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public IEntityStatusComponent getStatusComponent() {
        return statusComponent;
    }

    @Override
    public IEntityPositionComponent getPositionComponent() {
        return positionComponent;
    }

    @Override
    public IEventHandler getEventHandler() {
        return eventHandler;
    }
}