package packets.outgoing.rooms.prepare;

import habbo.rooms.components.gamemap.IRoomGameMap;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomRelativeMapComposer extends OutgoingPacket {
    public RoomRelativeMapComposer(IRoomGameMap gameMap) {
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
