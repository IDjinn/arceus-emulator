package packets.outgoing.session.hotel;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class AvailabilityStatusMessageComposer extends OutgoingPacket {
    public AvailabilityStatusMessageComposer() {
        super(OutgoingHeaders.AvailabilityStatusMessageComposer);
        appendBoolean(true);
        appendBoolean(false);
        appendBoolean(true);
    }
}
