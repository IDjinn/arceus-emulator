package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class HideDoorbellComposer extends OutgoingPacket {
    public HideDoorbellComposer() {
        super(OutgoingHeaders.HideDoorbellComposer);
    }
}
