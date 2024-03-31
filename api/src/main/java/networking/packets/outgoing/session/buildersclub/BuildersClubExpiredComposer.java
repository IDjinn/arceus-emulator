package networking.packets.outgoing.session.buildersclub;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

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
