package packets.outgoing.session.wardobe;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class UserClothesComposer extends OutgoingPacket {
    public UserClothesComposer() {
        super(OutgoingHeaders.UserClothesComposer);
        appendInt(0);
        appendInt(0);
    }
}
