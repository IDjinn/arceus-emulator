package packets.incoming.session;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.session.logindata.MeMenuSettingsComposer;
import packets.outgoing.session.logindata.UserDataComposer;
import packets.outgoing.session.logindata.UserPerksComposer;

@Singleton
public class RequestUserDataEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestUserDataEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        client.sendMessages(
                new UserDataComposer(client.getHabbo()),
                new UserPerksComposer(client.getHabbo()),
                new MeMenuSettingsComposer(client.getHabbo())
        );
    }
}
