package packets.outgoing.navigator;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class CanCreateRoomComposer extends OutgoingPacket {
    public CanCreateRoomComposer() {
        super(OutgoingHeaders.CanCreateRoomComposer);

        // TODO
        appendInt(0); // 0 = can create room, 1 = room limit reached
        appendInt(50); // room limit
    }
}
