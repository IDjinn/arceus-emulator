package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomOpenComposer extends OutgoingPacket {

    public RoomOpenComposer() {
        super(OutgoingHeaders.RoomOpenComposer);
    }
}
