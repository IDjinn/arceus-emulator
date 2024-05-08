package packets.outgoing.session.calendar;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class AdventCalendarDataComposer extends OutgoingPacket {
    public AdventCalendarDataComposer() {
        super(OutgoingHeaders.AdventCalendarDataComposer);
        this.appendString("xmas14");
        this.appendString("");
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
        this.appendInt(0);
    }
}
