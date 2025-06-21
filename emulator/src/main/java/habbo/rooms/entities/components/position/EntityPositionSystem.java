package habbo.rooms.entities.components.position;

import core.ecs.IDomain;
import habbo.rooms.components.gamemap.ITileMetadata;
import habbo.rooms.components.objects.items.floor.IFloorFloorItem;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.RoomEntity;
import habbo.rooms.entities.components.status.EntityStatusComponent;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stormpot.Timeout;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.SequencedCollection;
import java.util.concurrent.TimeUnit;

public class EntityPositionSystem implements Runnable {
    private static final Timeout PATHFINDER_DEFAULT_TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);
    private static final double PLAYER_HEIGHT = 2d;
    private final IDomain domain;

    public EntityPositionSystem(final IDomain domain) {
        this.domain = domain;
    }

    @Override
    public void run() {
        this.domain.getDominion().findEntitiesWith(EntityPositionComponent.class, EntityStatusComponent.class).stream().forEach(result -> {
            final var entity = result.entity();
            final var positionComponent = result.comp1();
            final var statusComponent = result.comp2();

            if (positionComponent.nextPosition == null && positionComponent.goal == null && positionComponent.walkPath.isEmpty())
                return;

            if (positionComponent.nextPosition != null) {
                if (positionComponent.onItem != null) {
//                    positionComponent.onItem.onStepOut(entity);
                    positionComponent.onItem = null;
                }

                positionComponent.position = positionComponent.nextPosition;
//                statusComponent.setOrCreate("dev.coordinates", positionComponent.position.toString());
//                final var topItem = entity.getRoom().getObjectManager().getTopFloorItemAt(positionComponent.position, -1);
//                if (topItem.isPresent()) {
//                    positionComponent.onItem = topItem.get();
//                    positionComponent.onItem.onStepIn(entity);
//                }

                if (positionComponent.position.equals(positionComponent.goal)) {
                    positionComponent.goal = null;
                    statusComponent.statusBuckets.remove(RoomEntityStatus.MOVE);
                }
                statusComponent.needUpdateStatus = true;
            }

            if (positionComponent.walkPath.isEmpty() && positionComponent.goal != null) {
//                final var path = positionComponent.race.futureWithTimeout(() -> entity.getRoom().getPathfinder().tracePath(this, positionComponent.position, positionComponent.goal), PATHFINDER_DEFAULT_TIMEOUT);
//                if (path.isPresent()) {
//                    positionComponent.walkPath.addAll(path.get());
//                    statusComponent.setOrCreate("dev.path.size", String.valueOf(positionComponent.walkPath.size()));
//                } else {
//                    statusComponent.setOrCreate("dev.path.size", "none - execution timeout");
//                }
            }

            if (positionComponent.walkPath.isEmpty()) {
                positionComponent.nextPosition = null;
                positionComponent.goal = null;
                statusComponent.statusBuckets.remove(RoomEntityStatus.MOVE);
                statusComponent.needUpdateStatus = true;
                return;
            }

            positionComponent.nextPosition = positionComponent.walkPath.removeFirst();

            assert positionComponent.nextPosition != null;
            positionComponent.direction = Direction.calculate(positionComponent.position.getX(), positionComponent.position.getY(), positionComponent.nextPosition.getX(), positionComponent.nextPosition.getY());

            statusComponent.statusBuckets.remove(RoomEntityStatus.SIT);
            statusComponent.statusBuckets.remove(RoomEntityStatus.LAY);
//            entity.getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.MOVE,
//                    STR."\{positionComponent.nextPosition.getX()},\{positionComponent.nextPosition.getY()},\{positionComponent.nextPosition.getZ()}"));
            statusComponent.needUpdateStatus = true;
        });
    }
}
