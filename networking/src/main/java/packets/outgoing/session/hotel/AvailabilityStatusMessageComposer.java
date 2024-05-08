package packets.outgoing.session.hotel;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class AvailabilityStatusMessageComposer extends OutgoingPacket {
    public AvailabilityStatusMessageComposer() {
        super(OutgoingHeaders.AvailabilityStatusMessageComposer);
        this.appendBoolean(true);
        this.appendBoolean(false);
        this.appendBoolean(true);
    }
}
