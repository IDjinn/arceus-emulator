package packets.outgoing.friends;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class MessengerInitComposer extends OutgoingPacket {
    public MessengerInitComposer(IHabbo habbo) {
        super(OutgoingHeaders.MessengerInitComposer);
        this.appendInt(Integer.MAX_VALUE);
        this.appendInt(1337);
        this.appendInt(Integer.MAX_VALUE);
        this.appendInt(0);
    }
}
