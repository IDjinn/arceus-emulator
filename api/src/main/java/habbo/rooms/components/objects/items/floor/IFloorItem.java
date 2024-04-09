package habbo.rooms.components.objects.items.floor;

import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.entities.IRoomEntity;

import java.util.Optional;

public interface IFloorItem extends IRoomItem, IFloorObject {
    public boolean isAtDoor();

    public int getRotation();

    public default boolean canSit(IRoomEntity entity) {
        return this.getFurniture().isCanSit();
    }

    public default boolean canLay(IRoomEntity entity) {
        return this.getFurniture().isCanLay();
    }

    public default boolean canWalk() {
        return this.getFurniture().isCanWalk();
    }
    public default boolean canWalk(IRoomEntity entity) {
        return this.getFurniture().isCanWalk();
    }

    public default boolean canUse(IRoomEntity entity) {
        return this.getFurniture().getInteractionModesCount() > 0;
    }

    public default boolean canStack(IRoomEntity entity) {
        return this.getFurniture().isCanStack();
    }

    public default boolean canStack() {
        return this.getFurniture().isCanStack();
    }

    public default Optional<Double> getStackHeight(IRoomEntity entity) {
        if (!this.canStack(entity))
            return Optional.empty();

        return Optional.of(this.getFurniture().getStackHeight());
    }

    public default Optional<Double> getStackHeight() {
        if (!this.canStack())
            return Optional.empty();

        return Optional.of(this.getFurniture().getStackHeight());
    }

    public default void onStepIn(IRoomEntity entity) {

    }

    public default void onStepOut(IRoomEntity entity) {

    }


    public default void onStackInItem(IFloorItem floorItem) {

    }

    public default void onStackOutItem(IFloorItem floorItem) {

    }
}
