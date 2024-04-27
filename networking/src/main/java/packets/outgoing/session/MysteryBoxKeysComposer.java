package packets.outgoing.session;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class MysteryBoxKeysComposer extends OutgoingPacket {
    public MysteryBoxKeysComposer() {
        super(OutgoingHeaders.MysteryBoxKeysComposer);
        appendString("");
        appendString("");
    }
}
