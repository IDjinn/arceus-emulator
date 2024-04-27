package packets.outgoing.navigator;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class NewNavigatorLiftedRoomsComposer extends OutgoingPacket {
    public NewNavigatorLiftedRoomsComposer() {
        super(OutgoingHeaders.NewNavigatorLiftedRoomsComposer);

        appendInt(0);
    }
}
