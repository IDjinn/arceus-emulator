package packets.incoming.catalog;

import com.google.inject.Inject;
import habbo.catalog.ICatalogManager;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.catalog.CatalogPageComposer;

public class RequestCatalogPage extends IncomingEvent {
    @Inject
    ICatalogManager catalogManager;

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestCatalogPageEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        var pageId = packet.readInt();
        var offerId = packet.readInt();
        var mode = packet.readString();

        var page = catalogManager.getCatalogPageForHabbo(pageId, client.getHabbo());
        if (page.isEmpty())
            return;

        client.sendMessage(new CatalogPageComposer(page.get(), client.getHabbo(), offerId, mode));
    }
}
