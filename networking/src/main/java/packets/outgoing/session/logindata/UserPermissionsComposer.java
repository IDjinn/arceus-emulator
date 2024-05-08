package packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserPermissionsComposer extends OutgoingPacket {
    public UserPermissionsComposer() {
        super(OutgoingHeaders.UserPermissionsComposer);
        this.appendInt(2);
        this.appendInt(1);
        this.appendBoolean(false);
    }
}
