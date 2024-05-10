package packets.outgoing.rooms.gamemap;

import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.gamemap.IRoomTile;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomRelativeMapComposer extends OutgoingPacket {
    private static final int STACKING_BLOCKED_FLAG = 0x4000;
    private static final int ENCODE_HEIGHT_FLAG = 0x0100;
    private static final int HEIGHT_FLAG = 0x4000;

    public RoomRelativeMapComposer(IRoomGameMap gameMap) {
        super(OutgoingHeaders.RoomRelativeMapComposer);
        this.appendInt(gameMap.getMapSize() / gameMap.getMaxY());
        this.appendInt(gameMap.getMapSize());

        for (int y = 0; y < gameMap.getMaxY(); y++) {
            for (int x = 0; x < gameMap.getMaxX(); x++) {
                final var tile = gameMap.getTile(x, y);
                serializeTileHeight(this, tile);
            }
        }
    }

    private static int encodeTileHeight(double height) {
        return (int) (height * ENCODE_HEIGHT_FLAG);
    }

    public static void serializeTileHeight(final OutgoingPacket packet, final IRoomTile tile) {
        final var relativeMapHeight = tile.getRelativeMapHeight();
        if (relativeMapHeight.isPresent()) {
            packet.appendShort(encodeTileHeight(relativeMapHeight.get()));
        } else {
            packet.appendShort(tile.getZ() | STACKING_BLOCKED_FLAG);
        }
    }
}
