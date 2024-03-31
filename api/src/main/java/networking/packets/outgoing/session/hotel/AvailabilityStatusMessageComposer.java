package networking.packets.outgoing.session.hotel;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class AvailabilityStatusMessageComposer extends OutgoingPacket {
    public AvailabilityStatusMessageComposer() {
        super(OutgoingHeaders.AvailabilityStatusMessageComposer);
        appendBoolean(true);
        appendBoolean(false);
        appendBoolean(true);
    }
}
