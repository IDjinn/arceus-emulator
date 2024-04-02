package packets.outgoing.friends;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class MessengerInitComposer extends OutgoingPacket {
    public MessengerInitComposer(IHabbo habbo) {
        super(OutgoingHeaders.MessengerInitComposer);
        appendInt(Integer.MAX_VALUE);
        appendInt(1337);
        appendInt(Integer.MAX_VALUE);
        appendInt(0);
    }
}
