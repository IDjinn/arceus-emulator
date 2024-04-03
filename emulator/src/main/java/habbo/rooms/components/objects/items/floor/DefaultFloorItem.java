package habbo.rooms.components.objects.items.floor;

import habbo.furniture.IFurniture;
import habbo.rooms.IRoom;
import habbo.rooms.components.objects.items.IRoomItemData;
import habbo.rooms.components.objects.items.RoomItem;
import networking.packets.OutgoingPacket;
import utils.Position;

public class DefaultFloorItem extends RoomItem implements IFloorObject {
    public static final String INTERACTION_NAME = "default_floor";

    public DefaultFloorItem(IRoomItemData itemData, IRoom room, IFurniture furniture) {
        super(itemData, room, furniture);

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


    @Override
    public OutgoingPacket serializePosition(OutgoingPacket packet) {
        return packet
                .appendInt(this.getPosition().getX())
                .appendInt(this.getPosition().getY())
                .appendInt(this.getRotation())
                .appendString(Double.toString(this.getPosition().getZ()))
                .appendString(Double.toString(this.getFurniture().getStackHeight()));
    }

    @Override
    public int getRotation() {
        return this.getItemData().getRotation();
    }

    @Override
    public String getExtraData() {
        return this.getItemData().getData();
    }

    @Override
    public void setExtraData(String extraData) {
        this.getItemData().setData(extraData);
    }

    @Override
    public OutgoingPacket serializeExtraData(OutgoingPacket packet) {
        return packet.appendString(this.getExtraData());
    }

    @Override
    public OutgoingPacket serializeLimitedData(OutgoingPacket packet) {
        return packet
                .appendInt(this.getLimitedData().getLimitedRare())
                .appendInt(this.getLimitedData().getLimitedRareTotal());
    }
}
