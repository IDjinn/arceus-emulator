package habbo.rooms.entities;

import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.floor.IFloorObject;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import habbo.rooms.entities.variables.IEntityVariableManager;
import networking.packets.IOutgoingPacket;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.cycle.ICycle;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.SequencedCollection;
import java.util.concurrent.ConcurrentHashMap;

public interface IRoomEntity extends ISerializable, ICycle, IFloorObject {
    int getVirtualId();

    IRoom getRoom();

    @Nullable IFloorItem getOnItem();

    String getName();

    void setName(String name);

    void serializeStatus(IOutgoingPacket packet);

    @Nullable Position getNextPosition();

    void setNextPosition(@Nullable Position position);

    Direction getDirection();

    void setDirection(Direction direction);

    ConcurrentHashMap<RoomEntityStatus, StatusBucket> getStatus();

    void setStatus(StatusBucket status);

    void removeStatus(RoomEntityStatus status);

    void setNeedUpdateStatus(boolean needUpdate);

    boolean isNeedUpdate();


    @Nullable Position getGoal();

    void setGoal(@Nullable Position goal);

    SequencedCollection<Position> getWalkPath();

    IEntityVariableManager getEntityVariablesManager();

    boolean canWalk();

    void setCanWalk(final boolean canWalk);
}
