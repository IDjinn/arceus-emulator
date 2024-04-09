package habbo.rooms.entities;

import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IPositionable;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.Direction;
import utils.Position;
import utils.cycle.ICycle;

import java.util.SequencedCollection;
import java.util.concurrent.ConcurrentHashMap;

public interface IRoomEntity extends ISerializable, ICycle, IPositionable {
    public int getVirtualId();

    public @Nullable IFloorItem getOnItem();

    public String getName();

    public void setName(String name);

    public IRoom getRoom();

    public void serializeStatus(OutgoingPacket packet);

    public @Nullable Position getNextPosition();

    public void setNextPosition(@Nullable Position position);

    public Direction getDirection();

    public void setDirection(Direction direction);

    public ConcurrentHashMap<RoomEntityStatus, StatusBucket> getStatus();

    public void setStatus(StatusBucket status);

    public void removeStatus(RoomEntityStatus status);

    public void setNeedUpdateStatus(boolean needUpdate);

    public boolean isNeedUpdate();


    public @Nullable Position getGoal();

    public void setGoal(@Nullable Position goal);

    public SequencedCollection<Position> getWalkPath();
}
