package packets.incoming.navigator;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.navigator.INavigatorManager;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.navigator.search.NewNavigatorSearchResultsComposer;

@Singleton
public class RequestNewNavigatorRoomsEvent extends IncomingEvent {
    @Inject
    private INavigatorManager navigatorManager;

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
