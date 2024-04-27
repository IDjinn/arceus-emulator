package packets.outgoing.session.buildersclub;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class BuildersClubExpiredComposer extends OutgoingPacket {
    public BuildersClubExpiredComposer() {
        super(OutgoingHeaders.BuildersClubExpiredComposer);
        appendInt(Integer.MAX_VALUE);
        appendInt(0);
        appendInt(100);
        appendInt(Integer.MAX_VALUE);
        appendInt(0);
    }
}
