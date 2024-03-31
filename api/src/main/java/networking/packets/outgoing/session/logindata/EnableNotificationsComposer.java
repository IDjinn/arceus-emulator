package networking.packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class EnableNotificationsComposer extends OutgoingPacket {
    public EnableNotificationsComposer() {
        super(OutgoingHeaders.EnableNotificationsComposer);
        appendBoolean(true);
    }
}
