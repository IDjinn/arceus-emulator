package habbo.rooms.entities;

import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import org.jetbrains.annotations.Nullable;
import utils.Direction;
import utils.Position;

import java.util.ArrayList;
import java.util.SequencedCollection;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RoomEntity implements IRoomEntity {
    private final ConcurrentHashMap<RoomEntityStatus, StatusBucket> statusBuckets;
    private final int virtualId;
    private String name;
    private final IRoom room;
    private Position position;
    private Direction direction;
    private boolean needUpdateStatus;
    private @Nullable Position goal;
    private SequencedCollection<Position> walkPath;
    private @Nullable Position nextPostion;
    private @Nullable IFloorItem onItem;

    public RoomEntity(IRoom room, int virtualId) {
        this.virtualId = virtualId;
        this.room = room;
        this.position = new Position(4, 5);
        this.direction = Direction.East;
        this.statusBuckets = new ConcurrentHashMap<>();
        this.walkPath = new ArrayList<>();
        onItem = null;
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
    public ConcurrentHashMap<RoomEntityStatus, StatusBucket> getStatus() {
        return statusBuckets;
    }

    @Override
    public @Nullable IFloorItem getOnItem() {
        return this.onItem;
    }

    @Override
    public void removeStatus(RoomEntityStatus status) {
        needUpdateStatus |= statusBuckets.remove(status) != null;
    }

    @Override
    public void setNeedUpdateStatus(boolean needUpdate) {
        needUpdateStatus = needUpdate;
    }

    @Override
    public boolean isNeedUpdate() {
        return needUpdateStatus;
    }

    @Override
    public void setStatus(StatusBucket bucket) {
        if (statusBuckets.containsKey(bucket.getStatus())) {
            var currentBucket = statusBuckets.get(bucket.getStatus());
            currentBucket.setTicks(bucket.getTicks());
            currentBucket.setValue(bucket.getValue());
        } else {
            statusBuckets.put(bucket.getStatus(), bucket);
        }

        this.setNeedUpdateStatus(true);
    }

    @Override
    public void tick() {
        this.handleStatus();
        this.handleWalking();
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
            final var topItem =
                    this.getRoom().getObjectManager().getTopFloorItemAt(this.getPosition(), -1);
            if (topItem.isPresent()) {
                this.onItem = topItem.get();
                this.onItem.onStepIn(this);
            }

            if (this.getPosition().equals(this.getGoal())) {
                this.setGoal(null);
                this.removeStatus(RoomEntityStatus.MOVE);
            }
            this.setNeedUpdateStatus(true);
        }

        if (this.walkPath.isEmpty() && this.getGoal() != null) {
            this.walkPath.addAll(this.getRoom().getPathfinder().tracePath(
                    this.getRoom().getGameMap(),
                    this.getPosition(),
                    this.getGoal()
            ));
        }

        if (walkPath.isEmpty()) {
            this.setNextPosition(null);
            this.setGoal(null);
            this.removeStatus(RoomEntityStatus.MOVE);
            this.setNeedUpdateStatus(true);
            return;
        }

        this.setNextPosition(walkPath.removeFirst());
        this.setDirection(Direction.calculate(
                this.getPosition().getX(),
                this.getPosition().getY(),
                this.getNextPosition().getX(),
                this.getNextPosition().getY()
        ));
        this.setStatus(new StatusBucket(RoomEntityStatus.MOVE, STR."\{this.getNextPosition().getX()},\{this.getNextPosition().getY()},\{this.getNextPosition().getZ()}"));
        this.setNeedUpdateStatus(true);
    }

    private void handleStatus() {
        synchronized (statusBuckets) {
            for (StatusBucket bucket : statusBuckets.values()) {
                if (bucket.getTicks() <= 0) {
                    this.removeStatus(bucket.getStatus());
                }
                bucket.decrementTick();
            }
        }
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
}
