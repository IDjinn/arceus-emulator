package packets.outgoing.session.habboclub;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserClubComposer extends OutgoingPacket {
    public UserClubComposer() {
        super(OutgoingHeaders.UserClubComposer);
        appendString("HABBO_CLUB".toLowerCase());
        appendInt(0);
        appendInt(0);
        appendInt(0);
        appendInt(0);
        appendBoolean(false);
        appendBoolean(false);
        appendInt(0);
        appendInt(0);
        appendInt(0);
        appendInt(0);
    }
}
