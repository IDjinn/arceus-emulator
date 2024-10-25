package habbo.rooms.entities.status;

import habbo.rooms.entities.IEntityComponent;
import networking.util.ISerializable;

import java.util.Map;

public interface IEntityStatusComponent extends ISerializable {
    Map<RoomEntityStatus, StatusBucket> getStatus();

    void setStatus(StatusBucket bucket);

    void removeStatus(RoomEntityStatus status);

    void setNeedUpdateStatus(boolean needUpdate);

    boolean isNeedUpdate();
}
