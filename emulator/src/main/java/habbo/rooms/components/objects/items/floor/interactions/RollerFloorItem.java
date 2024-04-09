package habbo.rooms.components.objects.items.floor.interactions;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.floor.AdvancedFloorItem;
import habbo.rooms.components.objects.items.floor.FloorItemEvent;
import habbo.rooms.entities.IRoomEntity;

public class RollerFloorItem extends AdvancedFloorItem implements FloorItemEvent.IEventListener {
    public static final String INTERACTION_NAME = "roller";
    private FloorItemEvent event;

    public RollerFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);
        this.event = this.createEvent(RollerFloorItem.class.getName());
        this.event.subscribeListener(this);
        this.enqueueEvent(this.event);
    }

    @Override
    public void onStepIn(final IRoomEntity entity) {
        super.onStepIn(entity);
    }

    @Override
    public void onEventComplete(FloorItemEvent event) {

    }
}
