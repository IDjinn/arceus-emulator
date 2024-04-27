package packets.outgoing.session.logindata;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class EnableNotificationsComposer extends OutgoingPacket {
    public EnableNotificationsComposer() {
        super(OutgoingHeaders.EnableNotificationsComposer);
        appendBoolean(true);
    }
}
