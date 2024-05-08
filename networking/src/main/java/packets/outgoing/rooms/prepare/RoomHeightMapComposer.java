package packets.outgoing.rooms.prepare;

import habbo.rooms.components.gamemap.IRoomGameMap;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomHeightMapComposer extends OutgoingPacket {
    public RoomHeightMapComposer(IRoomGameMap gameMap) {
        super(OutgoingHeaders.RoomHeightMapComposer);
        this.appendBoolean(true, "scale (true ? 32 : 64");
        this.appendInt(1, "wallHeight"); // TODO this
        this.appendString(gameMap.getModelMap());
    }
}
