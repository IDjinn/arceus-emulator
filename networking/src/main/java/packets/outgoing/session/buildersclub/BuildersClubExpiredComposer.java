package packets.outgoing.session.buildersclub;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class BuildersClubExpiredComposer extends OutgoingPacket {
    public BuildersClubExpiredComposer() {
        super(OutgoingHeaders.BuildersClubExpiredComposer);
        this.appendInt(Integer.MAX_VALUE);
        this.appendInt(0);
        this.appendInt(100);
        this.appendInt(Integer.MAX_VALUE);
        this.appendInt(0);
    }
}
