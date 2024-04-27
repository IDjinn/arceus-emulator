package packets.outgoing.session;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class CfhTopicsMessageComposer extends OutgoingPacket {
    public CfhTopicsMessageComposer() {
        super(OutgoingHeaders.CfhTopicsMessageComposer);
        appendInt(0);
    }
}
