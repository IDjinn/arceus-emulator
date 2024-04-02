package networking.packets.incoming.friends;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.packets.outgoing.friends.MessengerInitComposer;

@Singleton
public class RequestInitFriendsEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestInitFriendsEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        // TODO
        client.sendMessage(new MessengerInitComposer(client.getHabbo()));
    }
}
