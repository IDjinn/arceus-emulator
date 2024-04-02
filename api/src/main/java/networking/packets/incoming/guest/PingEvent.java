package networking.packets.incoming.guest;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.packets.outgoing.PingComposer;
import networking.util.NoAuth;

@Singleton
@NoAuth
public class PingEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.PongEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        client.sendMessage(new PingComposer());
    }
}
