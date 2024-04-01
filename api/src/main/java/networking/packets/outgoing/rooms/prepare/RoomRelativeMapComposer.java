package networking.packets.outgoing.rooms.prepare;

import habbohotel.rooms.components.gamemap.IRoomGameMapComponent;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomRelativeMapComposer extends OutgoingPacket {
    public RoomRelativeMapComposer(IRoomGameMapComponent gameMap) {
        super(OutgoingHeaders.RoomRelativeMapComposer);
        appendInt(gameMap.getMapSize() / gameMap.getMaxY());
        appendInt(gameMap.getMapSize());

        for (int y = 0; y < gameMap.getMaxY(); y++) {
            for (int x = 0; x < gameMap.getMaxX(); x++) {
                appendInt((int) (256d));
            }
        }
    }
}
