package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomFloorItemsComposer extends OutgoingPacket {
    public RoomFloorItemsComposer() {
        super(OutgoingHeaders.RoomFloorItemsComposer);
        appendInt(0);
        appendInt(0);
    }
}
