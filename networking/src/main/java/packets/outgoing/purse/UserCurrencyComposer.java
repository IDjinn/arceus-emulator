package packets.outgoing.purse;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserCurrencyComposer extends OutgoingPacket {
    public UserCurrencyComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserCurrencyComposer);
        appendString(9999 + ".0");
    }
}
