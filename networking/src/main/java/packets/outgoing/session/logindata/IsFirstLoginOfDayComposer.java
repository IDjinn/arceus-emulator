package packets.outgoing.session.logindata;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class IsFirstLoginOfDayComposer extends OutgoingPacket {
    public IsFirstLoginOfDayComposer() {
        super(OutgoingHeaders.IsFirstLoginOfDayComposer);
        appendBoolean(true);
    }
}
