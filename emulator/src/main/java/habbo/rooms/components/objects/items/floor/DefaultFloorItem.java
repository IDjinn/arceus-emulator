package habbo.rooms.components.objects.items.floor;

import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.RoomItem;
import utils.Position;

public class DefaultFloorItem extends RoomItem implements IFloorObject {
    public static final String INTERACTION_NAME = "default_floor";

    public DefaultFloorItem(IRoomItemData itemData, IRoom room) {
        super(itemData, room);

        this.setPosition(itemData.getPosition());
    }

    @Override
    public Position getPosition() {
        return this.getItemData().getPosition();
    }

    @Override
    public void setPosition(Position position) {
        this.getItemData().setPosition(position);
    }

    @Override
    public boolean isAtDoor() {
        return false;
    }
}
