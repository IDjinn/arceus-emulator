package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomPromotionMessageComposer extends OutgoingPacket {
    public RoomPromotionMessageComposer() {
        super(OutgoingHeaders.RoomPromotionMessageComposer);
        appendInt(-1);
        appendInt(-1);
        appendString("");
        appendInt(0);
        appendInt(0);
        appendString("");
        appendString("");
        appendInt(0);
        appendInt(0);
        appendInt(0);
    }
}
