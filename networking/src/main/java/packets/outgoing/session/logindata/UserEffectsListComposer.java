package packets.outgoing.session.logindata;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class UserEffectsListComposer extends OutgoingPacket {
    public UserEffectsListComposer() {
        super(OutgoingHeaders.UserEffectsListComposer);
        this.appendInt(0);
    }
}
