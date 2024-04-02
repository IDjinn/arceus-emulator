package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomFloorItemsComposer extends OutgoingPacket {
    public RoomFloorItemsComposer() {
        super(OutgoingHeaders.RoomFloorItemsComposer);
        appendInt(0);
        appendInt(0);
    }
}
