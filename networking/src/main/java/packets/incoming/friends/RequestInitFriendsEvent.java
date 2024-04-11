package packets.incoming.friends;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.friends.MessengerInitComposer;

@Singleton
public class RequestInitFriendsEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestInitFriendsEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        // TODO
        client.sendMessage(new MessengerInitComposer(client.getHabbo()));
    }
}
