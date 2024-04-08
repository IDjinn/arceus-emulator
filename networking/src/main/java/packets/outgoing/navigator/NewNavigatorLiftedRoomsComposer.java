package packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class NewNavigatorLiftedRoomsComposer extends OutgoingPacket {
    public NewNavigatorLiftedRoomsComposer() {
        super(OutgoingHeaders.NewNavigatorLiftedRoomsComposer);

        appendInt(0);
    }
}
