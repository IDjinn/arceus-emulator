package habbo.rooms.components.objects.items.floor;

import habbo.rooms.components.objects.items.IPositionable;
import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.entities.IRoomEntity;

public interface IFloorItem extends IRoomItem, IPositionable {
    public boolean isAtDoor();

    public int getRotation();

    public default boolean canSit(IRoomEntity entity) {
        return this.getFurniture().isCanSit();
    }

    public default boolean canLay(IRoomEntity entity) {
        return this.getFurniture().isCanLay();
    }

    public default boolean canWalk(IRoomEntity entity) {
        return this.getFurniture().isCanWalk();
    }

    public default boolean canUse(IRoomEntity entity) {
        return this.getFurniture().getInteractionModesCount() > 0;
    }
}
