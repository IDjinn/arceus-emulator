package packets.outgoing.rooms.objects.floor;

import habbo.rooms.components.objects.items.floor.IFloorItem;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RemoveFloorItemComposer extends OutgoingPacket {
    public RemoveFloorItemComposer(IFloorItem floorItem, int pickupPlayerId, int delay) {
        super(OutgoingHeaders.RemoveFloorItemComposer);

        appendString(String.valueOf(floorItem.getVirtualId()));
        appendBoolean(false, "isExpired");
        appendInt(pickupPlayerId);
        appendInt(delay);
    }

    public RemoveFloorItemComposer(IFloorItem floorItem, int pickupPlayerId) {
        this(floorItem, pickupPlayerId, 0);
    }
}
