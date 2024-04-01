package networking.packets.incoming.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbohotel.navigator.INavigatorManager;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.packets.outgoing.navigator.search.NewNavigatorSearchResultsComposer;

@Singleton
public class RequestNewNavigatorRoomsEvent extends IncomingEvent {
    private final INavigatorManager navigatorManager;

    @Inject
    public RequestNewNavigatorRoomsEvent(INavigatorManager navigatorManager) {
        this.navigatorManager = navigatorManager;
    }

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestNewNavigatorRoomsEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        var view = navigatorManager.validateView(packet.readString());
        var query = packet.readString();

        if (view.equals("query")) view = "hotel_view";
        if (view.equals("groups")) view = "hotel_view";

        var rooms = navigatorManager.getRoomsForView(view, query);
        client.sendMessage(new NewNavigatorSearchResultsComposer(view, query, rooms));
    }
}
