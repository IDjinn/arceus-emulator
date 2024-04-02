package networking.packets.outgoing.friends;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class MessengerInitComposer extends OutgoingPacket {
    public MessengerInitComposer(IHabbo habbo) {
        super(OutgoingHeaders.MessengerInitComposer);
        appendInt(Integer.MAX_VALUE);
        appendInt(1337);
        appendInt(Integer.MAX_VALUE);
        appendInt(0);
    }
}
