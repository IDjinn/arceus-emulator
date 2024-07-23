package habbo.rooms.entities;

import com.google.inject.Inject;
import core.concurrency.IRace;
import habbo.rooms.IRoom;
import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.entities.status.IEntityStatusComponent;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import org.jetbrains.annotations.Nullable;
import stormpot.Timeout;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.ArrayList;
import java.util.SequencedCollection;
import java.util.concurrent.TimeUnit;

public abstract class RoomEntity implements IRoomEntity {
    private final int virtualId;
    private String name;
    private final IRoom room;
    private Position position;
    private Direction direction;
    private @Nullable Position goal;
    private final SequencedCollection<Position> walkPath;
    private @Nullable Position nextPostion;
    private @Nullable IFloorItem onItem;

    private static final Timeout PATHFINDER_DEFAULT_TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);
    private @Inject IRace race;

    private @Inject IEntityStatusComponent statusComponent;

    public RoomEntity(IRoom room, int virtualId) {
        this.room = room;
        this.virtualId = virtualId;
        this.walkPath = new ArrayList<>();
        this.onItem = null;

        // TODO REMOVE ME
        final var door = this.getRoom().getGameMap().getTile(this.getRoom().getModel().getDoorX(), this.getRoom().getModel().getDoorY());
        this.position = door.getPosition();
        this.direction = this.getRoom().getModel().getDoorDirection();
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
    public Position getPosition() {
        return this.position;
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
    public @Nullable IFloorItem getOnItem() {
        return this.onItem;
    }


    @Override
    public synchronized void tick() {
        this.handleWalking();
    }

    @Override
    public boolean canSlideTo(final ITileMetadata metadata) {
        return metadata.getEntityHeight().isPresent();
    }

    private void handleWalking() {
        if (this.getNextPosition() == null && this.getGoal() == null && this.getWalkPath().isEmpty()) 
            return;

        if (this.getNextPosition() != null) {
            if (this.onItem != null) {
                this.onItem.onStepOut(this);
                this.onItem = null;
            }
            
            this.setPosition(this.getNextPosition());
            this.getEntityVariablesManager().setOrCreate("dev.coordinates", this.position.toString());
            final var topItem =
                    this.getRoom().getObjectManager().getTopFloorItemAt(this.getPosition(), -1);
            if (topItem.isPresent()) {
                this.onItem = topItem.get();
                this.onItem.onStepIn(this);
            }

            if (this.getPosition().equals(this.getGoal())) {
                this.setGoal(null);
                this.getStatusComponent().removeStatus(RoomEntityStatus.MOVE);
            }
            this.getStatusComponent().setNeedUpdateStatus(true);
        }

        if (this.walkPath.isEmpty() && this.getGoal() != null) {
            final var path = this.race.futureWithTimeout(() -> this.getRoom().getPathfinder().tracePath(
                    this,
                    this.getPosition(),
                    this.getGoal()
            ), PATHFINDER_DEFAULT_TIMEOUT);

            if (path.isPresent()) {
                this.walkPath.addAll(path.get());
                this.getEntityVariablesManager().setOrCreate("dev.path.size", String.valueOf(this.walkPath.size()));
            } else {
                this.getEntityVariablesManager().setOrCreate("dev.path.size", "none - execution timeout");
            }
        }

        if (this.walkPath.isEmpty()) {
            this.setNextPosition(null);
            this.setGoal(null);
            this.getStatusComponent().removeStatus(RoomEntityStatus.MOVE);
            this.getStatusComponent().setNeedUpdateStatus(true);
            return;
        }

        this.setNextPosition(this.walkPath.removeFirst());
        this.setDirection(Direction.calculate(
                this.getPosition().getX(),
                this.getPosition().getY(),
                this.getNextPosition().getX(),
                this.getNextPosition().getY()
        ));

        this.getStatusComponent().removeStatus(RoomEntityStatus.SIT);
        this.getStatusComponent().removeStatus(RoomEntityStatus.LAY);
        this.getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.MOVE, STR."\{this.getNextPosition().getX()},\{this.getNextPosition().getY()},\{this.getNextPosition().getZ()}"));
        this.getStatusComponent().setNeedUpdateStatus(true);
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
        return this.nextPostion;
    }

    @Override
    public void setNextPosition(@Nullable Position position) {
        this.nextPostion = position;
    }

    @Override
    public IEntityStatusComponent getStatusComponent() {
        return statusComponent;
    }
}
