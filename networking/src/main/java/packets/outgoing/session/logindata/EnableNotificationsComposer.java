package packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class EnableNotificationsComposer extends OutgoingPacket {
    public EnableNotificationsComposer() {
        super(OutgoingHeaders.EnableNotificationsComposer);
        this.appendBoolean(true);
    }
}
