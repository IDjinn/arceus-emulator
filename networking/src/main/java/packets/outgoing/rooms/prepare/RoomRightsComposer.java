package packets.outgoing.rooms.prepare;

import habbo.rooms.RoomRightLevel;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomRightsComposer extends OutgoingPacket {
    public RoomRightsComposer(RoomRightLevel level) {
        super(OutgoingHeaders.RoomRightsComposer);
        this.appendInt(level.ordinal());
    }
}
