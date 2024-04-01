package networking.packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class NewNavigatorLiftedRoomsComposer extends OutgoingPacket {
    public NewNavigatorLiftedRoomsComposer() {
        super(OutgoingHeaders.NewNavigatorLiftedRoomsComposer);
        appendInt(0);
    }
}
