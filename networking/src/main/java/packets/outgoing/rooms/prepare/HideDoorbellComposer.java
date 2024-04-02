package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class HideDoorbellComposer extends OutgoingPacket {
    public HideDoorbellComposer() {
        super(OutgoingHeaders.HideDoorbellComposer);
    }
}
