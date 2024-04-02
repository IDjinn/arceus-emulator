package networking.packets.incoming.purse;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.packets.outgoing.purse.UserCreditsComposer;
import networking.packets.outgoing.purse.UserCurrencyComposer;

@Singleton
public class RequestUserCreditsEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestUserCreditsEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        // TODO
        client.sendMessages(
                new UserCreditsComposer(client.getHabbo()),
                new UserCurrencyComposer(client.getHabbo())
        );
    }
}
