package packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class NewUserIdentityComposer extends OutgoingPacket {
    public NewUserIdentityComposer() {
        super(OutgoingHeaders.NewUserIdentityComposer);
        this.appendInt(1);
    }
}
