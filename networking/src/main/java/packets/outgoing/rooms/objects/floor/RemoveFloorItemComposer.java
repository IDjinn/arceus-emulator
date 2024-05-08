package packets.outgoing.rooms.objects.floor;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RemoveFloorItemComposer extends OutgoingPacket {
    public RemoveFloorItemComposer(IFloorItem floorItem, int pickupPlayerId, int delay) {
        super(OutgoingHeaders.RemoveFloorItemComposer);

        this.appendString(String.valueOf(floorItem.getVirtualId()));
        this.appendBoolean(false, "isExpired");
        this.appendInt(pickupPlayerId);
        this.appendInt(delay);
    }

    public RemoveFloorItemComposer(IFloorItem floorItem, int pickupPlayerId) {
        this(floorItem, pickupPlayerId, 0);
    }
}
