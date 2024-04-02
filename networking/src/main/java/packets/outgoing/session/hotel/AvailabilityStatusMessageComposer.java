package packets.outgoing.session.hotel;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class AvailabilityStatusMessageComposer extends OutgoingPacket {
    public AvailabilityStatusMessageComposer() {
        super(OutgoingHeaders.AvailabilityStatusMessageComposer);
        appendBoolean(true);
        appendBoolean(false);
        appendBoolean(true);
    }
}
