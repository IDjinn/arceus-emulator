package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomPaintComposer extends OutgoingPacket {
    public RoomPaintComposer(String type, String value) {
        super(OutgoingHeaders.RoomPaintComposer);
        appendString(type);
        appendString(value);
    }
}
