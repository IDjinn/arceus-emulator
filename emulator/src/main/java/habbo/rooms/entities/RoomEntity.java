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
        return this.statusComponent;
    }

    @Override
    public IEntityPositionComponent getPositionComponent() {
        return this.positionComponent;
    }

    @Override
    public IEventHandler getEventHandler() {
        return this.eventHandler;
    }
}