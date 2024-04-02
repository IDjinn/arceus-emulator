package packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class NewNavigatorMetaDataComposer extends OutgoingPacket {
    public NewNavigatorMetaDataComposer() {
        super(OutgoingHeaders.NewNavigatorMetaDataComposer);
        appendInt(4);
        appendString("official_view");
        appendInt(0);
        appendString("hotel_view");
        appendInt(0);
        appendString("roomads_view");
        appendInt(0);
        appendString("myworld_view");
        appendInt(0);
    }
}
