package packets.incoming.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.navigator.INavigatorManager;
import habbo.navigator.services.INavigatorSearchService;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RequestNewNavigatorRoomsEvent extends IncomingEvent {
    @Inject
    private INavigatorManager navigatorManager;

    @Inject
    private INavigatorSearchService navigatorSearchService;

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestNewNavigatorRoomsEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        String tabName = this.navigatorManager.normalizeTab(packet.readString());
        String query = packet.readString();

        this.navigatorSearchService.commit(client.getHabbo(), tabName, query);
    }
}
