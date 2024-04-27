package packets.outgoing.session.logindata;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class UserPermissionsComposer extends OutgoingPacket {
    public UserPermissionsComposer() {
        super(OutgoingHeaders.UserPermissionsComposer);
        appendInt(2);
        appendInt(1);
        appendBoolean(false);
    }
}
