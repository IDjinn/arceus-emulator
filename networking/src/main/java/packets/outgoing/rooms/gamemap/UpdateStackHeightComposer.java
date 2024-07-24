package packets.outgoing.rooms.gamemap;

import habbo.rooms.components.gamemap.IRoomTile;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.List;

public class UpdateStackHeightComposer extends OutgoingPacket {
    public UpdateStackHeightComposer(final List<IRoomTile> tiles) {
        super(OutgoingHeaders.UpdateStackHeightComposer);

        this.appendInt(tiles.size());
        for (final var tile : tiles) {
            this.appendInt(tile.getX());
            this.appendInt(tile.getY());
            RoomRelativeMapComposer.serializeTileHeight(this, tile);
        }
    }

    public UpdateStackHeightComposer(final IRoomTile tile) {
        super(OutgoingHeaders.UpdateStackHeightComposer);

        this.appendInt(1);
        this.appendInt(tile.getX());
        this.appendInt(tile.getY());
        RoomRelativeMapComposer.serializeTileHeight(this, tile);
    }
}
