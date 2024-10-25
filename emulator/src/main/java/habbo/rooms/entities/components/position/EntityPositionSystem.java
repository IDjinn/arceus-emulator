package habbo.rooms.entities.components.position;

import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.SequencedCollection;

public class EntityPositionSystem {

    private void handleWalking() {
        if (this.getNextPosition() == null && this.getGoal() == null && this.getWalkPath().isEmpty())
            return;

        if (this.getNextPosition() != null) {
            if (this.onItem != null) {
                this.onItem.onStepOut(this.getEntity());
                this.onItem = null;
            }

            this.setPosition(this.getNextPosition());
            this.getEntity().getEntityVariablesComponent().setOrCreate("dev.coordinates", this.position.toString());
            final var topItem =
                    this.getEntity().getRoom().getObjectManager().getTopFloorItemAt(this.getPosition(), -1);
            if (topItem.isPresent()) {
                this.onItem = topItem.get();
                this.onItem.onStepIn(this.getEntity());
            }

            if (this.getPosition().equals(this.getGoal())) {
                this.setGoal(null);
                this.getEntity().getStatusComponent().removeStatus(RoomEntityStatus.MOVE);
            }
            this.getEntity().getStatusComponent().setNeedUpdateStatus(true);
        }

        if (this.walkPath.isEmpty() && this.getGoal() != null) {
            final var path = this.race.futureWithTimeout(() -> this.getEntity().getRoom().getPathfinder().tracePath(
                    this,
                    this.getPosition(),
                    this.getGoal()
            ), PATHFINDER_DEFAULT_TIMEOUT);

            if (path.isPresent()) {
                this.walkPath.addAll(path.get());
                this.getEntity().getEntityVariablesComponent().setOrCreate("dev.path.size", String.valueOf(this.walkPath.size()));
            } else {
                this.getEntity().getEntityVariablesComponent().setOrCreate("dev.path.size", "none - execution timeout");
            }
        }

        if (this.walkPath.isEmpty()) {
            this.setNextPosition(null);
            this.setGoal(null);
            this.getEntity().getStatusComponent().removeStatus(RoomEntityStatus.MOVE);
            this.getEntity().getStatusComponent().setNeedUpdateStatus(true);
            return;
        }

        this.setNextPosition(this.walkPath.removeFirst());
        assert this.getNextPosition() != null;
        this.setDirection(Direction.calculate(
                this.getPosition().getX(),
                this.getPosition().getY(),
                this.getNextPosition().getX(),
                this.getNextPosition().getY()
        ));

        this.getEntity().getStatusComponent().removeStatus(RoomEntityStatus.SIT);
        this.getEntity().getStatusComponent().removeStatus(RoomEntityStatus.LAY);
        this.getEntity().getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.MOVE,
                STR."\{this.getNextPosition().getX()},\{this.getNextPosition().getY()},\{this.getNextPosition().getZ()}"));
        this.getEntity().getStatusComponent().setNeedUpdateStatus(true);
    }
}
