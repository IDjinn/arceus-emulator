package habbo.rooms.entities.status;

import habbo.rooms.entities.IEntityComponent;

import java.util.Map;

public interface IEntityStatusComponent extends IEntityComponent {
    Map<RoomEntityStatus, StatusBucket> getStatus();

    void setStatus(StatusBucket bucket);

    void removeStatus(RoomEntityStatus status);

    void setNeedUpdateStatus(boolean needUpdate);

    boolean isNeedUpdate();
}
