package networking.packets.outgoing.session.rooms;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class FavoriteRoomsCountComposer extends OutgoingPacket {
    public FavoriteRoomsCountComposer() {
        super(OutgoingHeaders.FavoriteRoomsCountComposer);
        appendInt(30);
        appendInt(0);
    }
}
