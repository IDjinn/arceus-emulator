package habbo.rooms.components.objects.items.floor.interactions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.DefaultFloorItem;
import habbo.rooms.entities.IRoomEntity;
import habbo.rooms.entities.status.RoomEntityStatus;
import habbo.rooms.entities.status.StatusBucket;
import utils.pathfinder.Direction;

public class SitFloorItem extends DefaultFloorItem {
    public static final String INTERACTION_NAME = "sit";

    public SitFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
    }

    @Override
    public void onStepIn(final IRoomEntity entity) {
        entity.getPositionComponent().setDirection(Direction.get(this.getRotation() % 8));
        final var position = entity.getPositionComponent().getPosition().copy();
        position.setZ(this.getPosition().getZ());
        entity.getPositionComponent().setPosition(position);
        entity.getStatusComponent().setStatus(new StatusBucket(RoomEntityStatus.SIT, STR."\{(this.getPosition().getZ() + 1D)}"));
        entity.getStatusComponent().setNeedUpdateStatus(true);
    }
}
