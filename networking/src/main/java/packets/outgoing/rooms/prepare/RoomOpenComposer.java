package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomOpenComposer extends OutgoingPacket {

    public RoomOpenComposer() {
        super(OutgoingHeaders.RoomOpenComposer);
    }
}
