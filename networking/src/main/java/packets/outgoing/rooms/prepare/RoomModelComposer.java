package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomModelComposer extends OutgoingPacket {
    public RoomModelComposer(String modelName, int roomId) {
        super(OutgoingHeaders.RoomModelComposer);
        this.appendString(modelName);
        this.appendInt(roomId);
    }
}
