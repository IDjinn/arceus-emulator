package networking.packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class IsFirstLoginOfDayComposer extends OutgoingPacket {
    public IsFirstLoginOfDayComposer() {
        super(OutgoingHeaders.IsFirstLoginOfDayComposer);
        appendBoolean(true);
    }
}
