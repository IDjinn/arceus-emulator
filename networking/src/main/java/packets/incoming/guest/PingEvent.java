package packets.incoming.guest;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import networking.util.NoAuth;
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
    public void parse(IIncomingPacket packet, IClient client) {
        client.sendMessage(new PingComposer());
    }
}
