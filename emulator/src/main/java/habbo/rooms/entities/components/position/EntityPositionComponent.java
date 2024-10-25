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

}
