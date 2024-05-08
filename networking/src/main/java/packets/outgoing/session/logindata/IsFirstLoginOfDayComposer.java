package packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class IsFirstLoginOfDayComposer extends OutgoingPacket {
    public IsFirstLoginOfDayComposer() {
        super(OutgoingHeaders.IsFirstLoginOfDayComposer);
        this.appendBoolean(true);
    }
}
