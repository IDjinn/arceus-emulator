package habbo.rooms.components.objects.items.floor.interactions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.AdvancedFloorItem;
import habbo.rooms.components.objects.items.floor.FloorItemEvent;
import habbo.rooms.components.objects.items.floor.IFloorObject;
import habbo.rooms.entities.IRoomEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.outgoing.rooms.objects.floor.SlideObjectBundleMessageComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class RollerFloorItem extends AdvancedFloorItem implements FloorItemEvent.IEventListener {
    public static final String INTERACTION_NAME = "roller";
    private final Queue<IFloorObject> objectsOnRoller;
    private final int DefaultRollerSpeed = 2;
    private Logger logger = LogManager.getLogger();
    private List<SlideObjectBundleMessageComposer.SlideObjectEntry> movementObject = new ArrayList<>();

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
    public void onEventComplete(FloorItemEvent event) {
        this.resetTimer();

        if (!this.getRoom().getGameMap().isValidCoordinate(this.getPosition().squareInFront(this.getRotation()))) {
            return;
        }

        this.movementObject.clear();
        synchronized (this.objectsOnRoller) {
            while (!this.objectsOnRoller.isEmpty()) {
                final var object = this.objectsOnRoller.poll();
                final var oldPosition = object.getPosition();
                object.setPosition(this.getPosition().squareInFront(this.getRotation())); // TODO NEW Z
                movementObject.add(new SlideObjectBundleMessageComposer.SlideObjectEntry(object.getVirtualId(),
                        oldPosition, object.getPosition()));
                if (object instanceof IRoomEntity entity) {
                    this.getRoom().broadcastMessage(new SlideObjectBundleMessageComposer(
                            entity,
                            SlideObjectBundleMessageComposer.RollerMovementType.Slide,
                            this.getPosition(),
                            this.getPosition().squareInFront(this.getRotation()),
                            movementObject,
                            this.getVirtualId()
                    ));
                } else {
                    this.getRoom().broadcastMessage(new SlideObjectBundleMessageComposer(
                            this.getPosition(),
                            this.getPosition().squareInFront(this.getRotation()),
                            movementObject,
                            this.getVirtualId()
                    ));
                }
            }
        }
    }
}
