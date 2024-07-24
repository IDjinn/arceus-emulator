package packets.outgoing.session.wardobe;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserClothesComposer extends OutgoingPacket {
    public UserClothesComposer() {
        super(OutgoingHeaders.UserClothesComposer);
        this.appendInt(0);
        this.appendInt(0);
    }
}
