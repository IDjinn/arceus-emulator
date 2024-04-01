package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

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
