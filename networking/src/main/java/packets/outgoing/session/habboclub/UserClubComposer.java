package packets.outgoing.session.habboclub;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserClubComposer extends OutgoingPacket {
    public UserClubComposer() {
        super(OutgoingHeaders.UserClubComposer);
        this.appendString("HABBO_CLUB".toLowerCase());
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
        this.appendBoolean(false);
        this.appendBoolean(false);
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
    }
}
