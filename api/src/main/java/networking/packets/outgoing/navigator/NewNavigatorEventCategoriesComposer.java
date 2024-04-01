package networking.packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class NewNavigatorEventCategoriesComposer extends OutgoingPacket {
    // TODO
    public NewNavigatorEventCategoriesComposer() {
        super(OutgoingHeaders.NewNavigatorEventCategoriesComposer);
        appendInt(0);
    }
}
