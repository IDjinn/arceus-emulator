package networking.packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class NewUserIdentityComposer extends OutgoingPacket {
    public NewUserIdentityComposer() {
        super(OutgoingHeaders.NewUserIdentityComposer);
        appendInt(1);
    }
}
