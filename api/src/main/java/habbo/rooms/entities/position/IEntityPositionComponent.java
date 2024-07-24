package habbo.rooms.entities.position;

import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.items.IFloorObject;
import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import habbo.rooms.entities.IEntityComponent;
import org.jetbrains.annotations.Nullable;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.SequencedCollection;

public interface IEntityPositionComponent extends IEntityComponent, IFloorObject {
    Direction getDirection();

    void setDirection(Direction direction);

    @Nullable
    IFloorFloorItem getOnItem();

    boolean canSlideTo(ITileMetadata metadata);

    @Nullable
    Position getGoal();

    void setGoal(@Nullable Position goal);

    SequencedCollection<Position> getWalkPath();

    @Nullable
    Position getNextPosition();

    void setNextPosition(@Nullable Position position);
}
