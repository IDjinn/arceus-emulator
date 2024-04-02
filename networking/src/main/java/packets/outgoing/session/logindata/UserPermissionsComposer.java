package packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserPermissionsComposer extends OutgoingPacket {
    public UserPermissionsComposer() {
        super(OutgoingHeaders.UserPermissionsComposer);
        appendInt(2);
        appendInt(1);
        appendBoolean(false);
    }
}
