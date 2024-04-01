package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomRightsComposer extends OutgoingPacket {
    public RoomRightsComposer(int level) {
        super(OutgoingHeaders.RoomRightsComposer);
        appendInt(level);
    }
}
