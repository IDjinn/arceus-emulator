package networking.packets.outgoing.guest;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class SecureLoginOkComposer extends OutgoingPacket {
    public SecureLoginOkComposer() {
        super(OutgoingHeaders.SecureLoginOKComposer);
    }
}
