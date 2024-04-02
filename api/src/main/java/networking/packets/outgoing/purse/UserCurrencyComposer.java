package networking.packets.outgoing.purse;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class UserCurrencyComposer extends OutgoingPacket {
    public UserCurrencyComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserCurrencyComposer);
        appendString(9999 + ".0");
    }
}
