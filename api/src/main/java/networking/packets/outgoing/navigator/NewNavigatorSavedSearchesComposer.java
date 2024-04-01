package networking.packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class NewNavigatorSavedSearchesComposer extends OutgoingPacket {
    // TODO
    public NewNavigatorSavedSearchesComposer() {
        super(OutgoingHeaders.NewNavigatorSavedSearchesComposer);
        appendInt(0);
    }
}
