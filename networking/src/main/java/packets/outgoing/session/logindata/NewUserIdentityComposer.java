package packets.outgoing.session.logindata;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class NewUserIdentityComposer extends OutgoingPacket {
    public NewUserIdentityComposer() {
        super(OutgoingHeaders.NewUserIdentityComposer);
        appendInt(1);
    }
}
