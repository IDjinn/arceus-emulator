package packets.outgoing.rooms.prepare;

import habbo.rooms.RoomRightLevel;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomRightsComposer extends OutgoingPacket {
    public RoomRightsComposer(RoomRightLevel level) {
        super(OutgoingHeaders.RoomRightsComposer);
        appendInt(level.ordinal());
    }
}
