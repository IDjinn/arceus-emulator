package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class HotelViewComposer extends OutgoingPacket {
    public HotelViewComposer() {
        super(OutgoingHeaders.HotelViewComposer);
    }
}
