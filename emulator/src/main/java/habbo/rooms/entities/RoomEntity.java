package habbo.rooms.entities;

import com.google.inject.Inject;
import habbo.rooms.IRoom;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.IEntityStatusComponent;

public abstract class RoomEntity implements IRoomEntity {
    private final int virtualId;
    private String name;
    private final IRoom room;

    private @Inject IEntityStatusComponent statusComponent;
    private @Inject IEntityPositionComponent positionComponent;

    public RoomEntity(IRoom room, int virtualId) {
        this.room = room;
        this.virtualId = virtualId;
    }

    @Override
    public int getVirtualId() {
        return this.virtualId;
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
}
