package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class HotelViewComposer extends OutgoingPacket {
    public HotelViewComposer() {
        super(OutgoingHeaders.HotelViewComposer);
    }
}
