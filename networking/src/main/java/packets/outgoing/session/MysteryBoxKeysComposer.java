package packets.outgoing.session;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class MysteryBoxKeysComposer extends OutgoingPacket {
    public MysteryBoxKeysComposer() {
        super(OutgoingHeaders.MysteryBoxKeysComposer);
        appendString("");
        appendString("");
    }
}
