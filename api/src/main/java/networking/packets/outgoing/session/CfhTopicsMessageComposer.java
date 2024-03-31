package networking.packets.outgoing.session;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class CfhTopicsMessageComposer extends OutgoingPacket {
    public CfhTopicsMessageComposer() {
        super(OutgoingHeaders.CfhTopicsMessageComposer);
        appendInt(0);
    }
}
