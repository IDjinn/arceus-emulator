package packets.incoming.guest;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import networking.util.NoAuth;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.PingComposer;

@Singleton
@NoAuth
public class PingEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.PongEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        client.sendMessage(new PingComposer());
    }
}
