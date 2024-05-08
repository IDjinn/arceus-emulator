package packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class CanCreateRoomComposer extends OutgoingPacket {
    public CanCreateRoomComposer() {
        super(OutgoingHeaders.CanCreateRoomComposer);

        // TODO
        this.appendInt(0); // 0 = can create room, 1 = room limit reached
        this.appendInt(50); // room limit
    }
}
