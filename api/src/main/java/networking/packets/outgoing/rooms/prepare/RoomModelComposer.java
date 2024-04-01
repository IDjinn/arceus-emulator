package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomModelComposer extends OutgoingPacket {
    public RoomModelComposer(String modelName, int roomId) {
        super(OutgoingHeaders.RoomModelComposer);
        appendString(modelName);
        appendInt(roomId);
    }
}
