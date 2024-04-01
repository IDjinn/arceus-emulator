package networking.packets.outgoing.rooms.prepare;

import habbohotel.rooms.components.gamemap.IRoomGameMapComponent;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomHeightMapComposer extends OutgoingPacket {
    public RoomHeightMapComposer(IRoomGameMapComponent gameMap) {
        super(OutgoingHeaders.RoomHeightMapComposer);
        appendBoolean(true);
        appendInt(1);
        appendString(gameMap.getModelMap());
    }
}
