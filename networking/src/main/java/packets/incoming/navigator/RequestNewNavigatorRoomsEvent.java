package packets.incoming.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.navigator.INavigatorManager;
import habbo.navigator.services.INavigatorSearchService;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.search.NewNavigatorSearchResultsComposer;

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
    public void Parse(IncomingPacket packet, INitroClient client) {
        String tabName = this.navigatorManager.normalizeTab(packet.readString());
        String query = packet.readString();

        navigatorSearchService.commit(client.getHabbo(), tabName, query);
    }
}
