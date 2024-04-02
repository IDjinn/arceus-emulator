package packets.outgoing.session;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class CfhTopicsMessageComposer extends OutgoingPacket {
    public CfhTopicsMessageComposer() {
        super(OutgoingHeaders.CfhTopicsMessageComposer);
        appendInt(0);
    }
}
