package packets.outgoing.rooms.prepare;

import habbo.rooms.components.gamemap.IRoomGameMap;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomHeightMapComposer extends OutgoingPacket {
    public RoomHeightMapComposer(IRoomGameMap gameMap) {
        super(OutgoingHeaders.RoomHeightMapComposer);
        appendBoolean(true);
        appendInt(1);
        appendString(gameMap.getModelMap());
    }
}
