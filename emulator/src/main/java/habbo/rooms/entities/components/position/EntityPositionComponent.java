package habbo.rooms.entities.components.position;

import com.google.inject.Inject;
import core.concurrency.IRace;
import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.position.IEntityPositionComponent;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Timeout;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.ArrayList;
import java.util.SequencedCollection;
import java.util.concurrent.TimeUnit;

public class EntityPositionComponent implements IEntityPositionComponent {
    private static final Timeout PATHFINDER_DEFAULT_TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);
    private static final double PLAYER_HEIGHT = 2d;
    private final SequencedCollection<Position> walkPath = new ArrayList<>();

    private @Nullable Position goal;
    private @Nullable Position nextPosition;
    private @Nullable IFloorFloorItem onItem;
    private @Inject IRace race;

    private Position position;
    private Direction direction;

    private @NotNull IRoomEntity entity;

    public void init(final IRoomEntity entity) {
        this.entity = entity;

        // TODO REMOVE ME
        final var door = this.getEntity().getRoom().getGameMap().getTile(this.getEntity().getRoom().getModel().getDoorX(),
                this.getEntity().getRoom().getModel().getDoorY());
        this.position = door.getPosition();
        this.direction = this.getEntity().getRoom().getModel().getDoorDirection();
    }

    @Override
    public @NotNull IRoomEntity getEntity() {
        return this.entity;
    }

    @Override
    public void tick() {
        this.handleWalking();
    }

    // TODO: BREAKING SOLID BUT...
    @Override
    public int getVirtualId() {
        return this.getEntity().getVirtualId();
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public double getHeight() {
        return PLAYER_HEIGHT;
    }

    @Override
    public void setPosition(Position position) {
        assert position != null;
        this.position = position;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void setDirection(Direction direction) {
        assert direction != null;
        this.direction = direction;
    }


    @Override
    public @Nullable IFloorFloorItem getOnItem() {
        return this.onItem;
    }

    // TODO
    @Override
    public boolean canSlideTo(final ITileMetadata metadata) {
        return metadata.getEntityHeight().isPresent();
    }

    @Override
    public @Nullable Position getGoal() {
        return this.goal;
    }

    @Override
    public void setGoal(@Nullable Position goal) {
        this.goal = goal;
    }

    @Override
    public SequencedCollection<Position> getWalkPath() {
        return this.walkPath;
    }

    @Override
    public @Nullable Position getNextPosition() {
        return this.nextPosition;
    }

    @Override
    public void setNextPosition(@Nullable Position position) {
        this.nextPosition = position;
    }

    private void handleWalking() {
        if (this.getNextPosition() == null && this.getGoal() == null && this.getWalkPath().isEmpty())
            return;

        if (this.getNextPosition() != null) {
            if (this.onItem != null) {
                this.onItem.onStepOut(this.getEntity());
                this.onItem = null;
            }

            this.setPosition(this.getNextPosition());
            this.getEntity().getEntityVariablesManager().setOrCreate("dev.coordinates", this.position.toString());
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
                this.getEntity().getEntityVariablesManager().setOrCreate("dev.path.size", String.valueOf(this.walkPath.size()));
            } else {
                this.getEntity().getEntityVariablesManager().setOrCreate("dev.path.size", "none - execution timeout");
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
