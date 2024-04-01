package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomOpenComposer extends OutgoingPacket {

    public RoomOpenComposer() {
        super(OutgoingHeaders.RoomOpenComposer);
    }
}
