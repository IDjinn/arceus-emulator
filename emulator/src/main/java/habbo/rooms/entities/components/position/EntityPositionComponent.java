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

public final class EntityPositionComponent  {
    public final SequencedCollection<Position> walkPath = new ArrayList<>();

    public @Nullable Position goal;
    public @Nullable Position nextPosition;
    public @Nullable IFloorFloorItem onItem;
    public @Inject IRace race;

    public Position position;
    public Direction direction;

//    public void init(final IRoomEntity entity) {
//        this.entity = entity;
//
//        // TODO REMOVE ME
//        final var door = this.getEntity().getRoom().getGameMap().getTile(this.getEntity().getRoom().getModel().getDoorX(),
//                this.getEntity().getRoom().getModel().getDoorY());
//        this.position = door.getPosition();
//        this.direction = this.getEntity().getRoom().getModel().getDoorDirection();
//    }
}
