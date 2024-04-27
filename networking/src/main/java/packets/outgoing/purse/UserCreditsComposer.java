package packets.outgoing.purse;

import habbo.habbos.IHabbo;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class UserCreditsComposer extends OutgoingPacket {
    public UserCreditsComposer(IHabbo habbo) {
        super(OutgoingHeaders.UserCreditsComposer);

        appendString(STR."\{habbo.getData().getCredits()}");
    }
}
