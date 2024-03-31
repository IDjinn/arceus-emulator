package networking.packets.outgoing;

import networking.packets.OutgoingPacket;

public class PingComposer extends OutgoingPacket {
    public PingComposer() {
        super(3928);
    }
}
