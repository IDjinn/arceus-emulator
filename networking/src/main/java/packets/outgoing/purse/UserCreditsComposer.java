package packets.outgoing.purse;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class UserCreditsComposer extends OutgoingPacket {
    public UserCreditsComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserCreditsComposer);

        this.appendString(STR."\{habbo.getData().getCredits()}");
    }
}
