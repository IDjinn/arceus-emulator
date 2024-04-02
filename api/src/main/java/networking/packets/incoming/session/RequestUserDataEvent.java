package networking.packets.incoming.session;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.packets.outgoing.session.logindata.MeMenuSettingsComposer;
import networking.packets.outgoing.session.logindata.UserDataComposer;
import networking.packets.outgoing.session.logindata.UserPerksComposer;

@Singleton
public class RequestUserDataEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestUserDataEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        client.sendMessages(
                new UserDataComposer(client.getHabbo()),
                new UserPerksComposer(client.getHabbo()),
                new MeMenuSettingsComposer(client.getHabbo())
        );
    }
}
