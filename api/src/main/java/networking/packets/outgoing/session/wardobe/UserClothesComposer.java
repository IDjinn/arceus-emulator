package networking.packets.outgoing.session.wardobe;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class UserClothesComposer extends OutgoingPacket {
    public UserClothesComposer() {
        super(OutgoingHeaders.UserClothesComposer);
        appendInt(0);
        appendInt(0);
    }
}
