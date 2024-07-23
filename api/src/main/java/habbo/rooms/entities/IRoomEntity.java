package habbo.rooms.entities;

import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.floor.IFloorObject;
import habbo.rooms.entities.status.IEntityStatusComponent;
import habbo.rooms.entities.variables.IEntityVariableManager;
import networking.util.ISerializable;
import org.jetbrains.annotations.Nullable;
import utils.cycle.ICycle;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.SequencedCollection;

public interface IRoomEntity extends ISerializable, ICycle, IFloorObject {
    int getVirtualId();

    IRoom getRoom();

    @Nullable IFloorItem getOnItem();

    String getName();

    void setName(String name);


    @Nullable Position getNextPosition();

    void setNextPosition(@Nullable Position position);

    Direction getDirection();

    void setDirection(Direction direction);

    @Nullable Position getGoal();

    void setGoal(@Nullable Position goal);

    SequencedCollection<Position> getWalkPath();

    IEntityVariableManager getEntityVariablesManager();

    IEntityStatusComponent getStatusComponent();
}
