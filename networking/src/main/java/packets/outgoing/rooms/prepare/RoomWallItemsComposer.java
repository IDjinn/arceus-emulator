package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomWallItemsComposer extends OutgoingPacket {
    public RoomWallItemsComposer() {
        super(OutgoingHeaders.RoomWallItemsComposer);
        appendInt(0);
        appendInt(0);
    }
}
