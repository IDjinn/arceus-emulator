package packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserEffectsListComposer extends OutgoingPacket {
    public UserEffectsListComposer() {
        super(OutgoingHeaders.UserEffectsListComposer);
        this.appendInt(0);
    }
}
