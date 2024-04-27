package packets.outgoing.guest;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class SecureLoginOkComposer extends OutgoingPacket {
    public SecureLoginOkComposer() {
        super(OutgoingHeaders.SecureLoginOKComposer);
    }
}
