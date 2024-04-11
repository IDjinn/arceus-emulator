package habbo.rooms.components.objects.items.floor;

import habbo.rooms.components.objects.items.IRoomItem;
import habbo.rooms.entities.IRoomEntity;
import utils.pathfinder.Position;

import java.util.Optional;

public interface IFloorItem extends IRoomItem, IFloorObject {
    boolean isAtDoor();

    int getRotation();

    default boolean canSit(IRoomEntity entity) {
        return this.getFurniture().isCanSit();
    }

    default boolean canLay(IRoomEntity entity) {
        return this.getFurniture().isCanLay();
    }

    default boolean canWalk() {
        return this.getFurniture().isCanWalk();
    }

    default boolean canWalk(IRoomEntity entity) {
        return this.getFurniture().isCanWalk();
    }

    default boolean canUse(IRoomEntity entity) {
        return this.getFurniture().getInteractionModesCount() > 0;
    }

    default boolean canStack(IRoomEntity entity) {
        return this.getFurniture().isCanStack();
    }

    default boolean canStack() {
        return this.getFurniture().isCanStack();
    }

    default Optional<Double> getStackHeight(IRoomEntity entity) {
        if (!this.canStack(entity))
            return Optional.empty();

        return Optional.of(this.getFurniture().getStackHeight());
    }

    default Optional<Double> getStackHeight() {
        if (!this.canStack())
            return Optional.empty();

        return Optional.of(this.getFurniture().getStackHeight());
    }

    default void onStepIn(IRoomEntity entity) {

    }

    default void onStepOut(IRoomEntity entity) {

    }


    default void onStackInItem(IFloorItem floorItem) {

    }

    default void onStackOutItem(IFloorItem floorItem) {

    }

    void setRotation(int rotation);

    default void onMove(Position oldPosition) {
        
    }
}
