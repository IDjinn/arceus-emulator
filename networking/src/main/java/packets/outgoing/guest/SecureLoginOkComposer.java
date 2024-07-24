package packets.outgoing.guest;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class SecureLoginOkComposer extends OutgoingPacket {
    public SecureLoginOkComposer() {
        super(OutgoingHeaders.SecureLoginOKComposer);
    }
}
