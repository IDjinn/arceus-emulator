package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomRightsComposer extends OutgoingPacket {
    public RoomRightsComposer(int level) {
        super(OutgoingHeaders.RoomRightsComposer);
        appendInt(level);
    }
}
