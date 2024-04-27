package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class HideDoorbellComposer extends OutgoingPacket {
    public HideDoorbellComposer() {
        super(OutgoingHeaders.HideDoorbellComposer);
    }
}
