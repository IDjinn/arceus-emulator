package habbo.rooms.components.objects.items.floor.interactions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.AdvancedFloorItem;
import habbo.rooms.components.objects.items.floor.FloorItemEvent;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import habbo.rooms.components.objects.items.floor.IFloorObject;
import habbo.rooms.entities.IRoomEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.objects.floor.SlideObjectBundleMessageComposer;

import java.util.PriorityQueue;
import java.util.Queue;

public class RollerFloorItem extends AdvancedFloorItem implements FloorItemEvent.IEventListener {
    public static final String INTERACTION_NAME = "roller";
    private final Queue<IFloorObject> objectsOnRoller;
    private final int DefaultRollerSpeed = 2;
    private Logger logger = LogManager.getLogger();

    public RollerFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);

        this.objectsOnRoller = new PriorityQueue<>(5);
        this.resetTimer();
    }

    @Override
    public void onStepIn(final IRoomEntity entity) {
        synchronized (this.objectsOnRoller) {
            this.objectsOnRoller.add(entity);
        }
    }

    @Override
    public void onStepOut(final IRoomEntity entity) {
        synchronized (this.objectsOnRoller) {
            this.objectsOnRoller.remove(entity);
        }
    }

    private Queue<SlideObjectBundleMessageComposer.SlideObjectEntry> movementObjects = new PriorityQueue<>();
    private SlideObjectBundleMessageComposer.SlideObjectEntry movementEntity = null;

    private void resetTimer() {
        var event = this.createEvent(RollerFloorItem.class.getName());
        if (event != null) {
            event.setTotalTicks(DefaultRollerSpeed);
            event.subscribeListener(this);
            this.enqueueEvent(event);
        } else {
            logger.error("Failed to create event for RollerFloorItem");
        }
    }

    @Override
    public void onStackInItem(final IFloorItem floorItem) {
        synchronized (this.objectsOnRoller) {
            this.objectsOnRoller.add(floorItem);
        }
    }

    @Override
    public void onStackOutItem(final IFloorItem floorItem) {
        synchronized (this.objectsOnRoller) {
            this.objectsOnRoller.remove(floorItem);
        }
    }

    @Override
    public void onEventComplete(FloorItemEvent event) {
        this.resetTimer();

        if (!this.getRoom().getGameMap().isValidCoordinate(this.getPosition().squareInFront(this.getRotation()))) {
            return;
        }

        this.movementObjects.clear();
        this.movementEntity = null;
        synchronized (this.objectsOnRoller) {
            while (!this.objectsOnRoller.isEmpty()) {
                final var object = this.objectsOnRoller.poll();
                final var oldPosition = object.getPosition();

                object.setPosition(this.getPosition().squareInFront(this.getRotation())); // TODO NEW Z
                if (this.movementEntity == null && object instanceof IRoomEntity) {
                    this.movementEntity = new SlideObjectBundleMessageComposer.SlideObjectEntry(object.getVirtualId(),
                            oldPosition, object.getPosition());
                } else {
                    this.movementObjects.add(new SlideObjectBundleMessageComposer.SlideObjectEntry(object.getVirtualId(),
                            oldPosition, object.getPosition()));
                }
            }

            if (this.movementEntity != null) {
                this.getRoom().broadcastMessage(new SlideObjectBundleMessageComposer(
                        this.movementEntity,
                        SlideObjectBundleMessageComposer.RollerMovementType.Slide,
                        this.movementEntity.oldPosition(),
                        this.getPosition().squareInFront(this.getRotation()),
                        this.movementObjects,
                        this.getVirtualId()
                ));
            } else if (!this.movementObjects.isEmpty()) {
                this.getRoom().broadcastMessage(new SlideObjectBundleMessageComposer(
                        this.getPosition(),
                        this.getPosition().squareInFront(this.getRotation()),
                        this.movementObjects,
                        this.getVirtualId()
                ));
            }
        }
    }
}