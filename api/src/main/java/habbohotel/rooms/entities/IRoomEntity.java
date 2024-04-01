package habbohotel.rooms.entities;

import habbohotel.rooms.IRoom;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import utils.Direction;
import utils.Position;

import java.util.concurrent.ConcurrentHashMap;

public interface IRoomEntity extends ISerializable {
    public int getVirtualId();

    public String getName();

    public void setName(String name);

    public IRoom getRoom();

    public void serializeStatus(OutgoingPacket packet);

    public Position getPosition();

    public void setPosition(Position position);

    public Direction getDirection();

    public void setDirection(Direction direction);

    public ConcurrentHashMap<RoomEntityStatus, StatusBucket> getStatus();

    public void setStatus(StatusBucket status);

    public void removeStatus(RoomEntityStatus status);

    public void setNeedUpdateStatus(boolean needUpdate);
}
