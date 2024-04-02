package networking.packets.outgoing.purse;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class UserCreditsComposer extends OutgoingPacket {
    public UserCreditsComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserCreditsComposer);
        appendString(9999 + ".0");
    }
}
