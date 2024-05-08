package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomPromotionMessageComposer extends OutgoingPacket {
    public RoomPromotionMessageComposer() {
        super(OutgoingHeaders.RoomPromotionMessageComposer);
        this.appendInt(-1);
        this.appendInt(-1);
        this.appendString("");
        this.appendInt(0);
        this.appendInt(0);
        this.appendString("");
        this.appendString("");
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
    }
}
