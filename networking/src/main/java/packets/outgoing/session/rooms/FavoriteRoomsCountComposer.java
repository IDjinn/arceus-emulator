package packets.outgoing.session.rooms;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class FavoriteRoomsCountComposer extends OutgoingPacket {
    public FavoriteRoomsCountComposer() {
        super(OutgoingHeaders.FavoriteRoomsCountComposer);
        appendInt(30);
        appendInt(0);
    }
}
