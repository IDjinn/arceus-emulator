package packets.outgoing.session.calendar;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

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
