package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomModelComposer extends OutgoingPacket {
    public RoomModelComposer(String modelName, int roomId) {
        super(OutgoingHeaders.RoomModelComposer);
        appendString(modelName);
        appendInt(roomId);
    }
}
