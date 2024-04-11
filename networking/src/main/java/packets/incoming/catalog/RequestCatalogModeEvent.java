package packets.incoming.catalog;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.catalog.ICatalogManager;
import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import networking.packets.OutgoingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.catalog.CatalogIndexComposer;

@Singleton
public class RequestCatalogModeEvent extends IncomingEvent {
    private final int RootPageId = -1;

    @Inject
    private ICatalogManager catalogManager;

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestCatalogModeEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        var catalogMode = "normal";// THIS MODE IS JUST FOR FLASH. DOES NOT WORK WITH NITRO packet.readString();
        if (catalogMode == null)
            return;
        if (!catalogMode.equals("normal"))
            throw new IllegalArgumentException("Invalid catalog mode");

        client.sendMessage(new OutgoingPacket(3828).appendInt(0));
        client.sendMessage(new CatalogIndexComposer(client.getHabbo(), catalogMode, this.catalogManager.getCatalogPagesForHabbo(this.RootPageId, client.getHabbo())));
    }
}
