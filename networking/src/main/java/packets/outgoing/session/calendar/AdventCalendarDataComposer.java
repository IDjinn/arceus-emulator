package packets.outgoing.session.calendar;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class AdventCalendarDataComposer extends OutgoingPacket {
    public AdventCalendarDataComposer() {
        super(OutgoingHeaders.AdventCalendarDataComposer);
        appendString("xmas14");
        appendString("");
        appendInt(0);
        appendInt(0);
        appendInt(0);
        appendInt(0);
    }
}
