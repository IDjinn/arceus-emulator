package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomPaintComposer extends OutgoingPacket {
    public RoomPaintComposer(String type, String value) {
        super(OutgoingHeaders.RoomPaintComposer);
        appendString(type);
        appendString(value);
    }
}
