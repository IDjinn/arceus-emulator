package packets.incoming.purse;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.purse.UserCreditsComposer;
import packets.outgoing.purse.UserCurrencyComposer;

@Singleton
public class RequestUserCreditsEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestUserCreditsEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        client.sendMessages(
                new UserCreditsComposer(client.getHabbo()),
                new UserCurrencyComposer(client.getHabbo())
        );
    }
}
