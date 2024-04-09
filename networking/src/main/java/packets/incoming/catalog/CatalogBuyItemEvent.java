package packets.incoming.catalog;

import com.google.inject.Inject;
import habbo.catalog.ICatalogManager;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

public class CatalogBuyItemEvent extends IncomingEvent {
    @Inject
    ICatalogManager catalogManager;

    @Override
    public int getHeaderId() {
        return IncomingHeaders.CatalogBuyItemEvent;
    }

    @Override
    public void parse(IncomingPacket packet, INitroClient client) {
        var pageId = packet.readInt();
        var page = catalogManager.getCatalogPageForHabbo(pageId, client.getHabbo());
        if (page == null)
            return;

        var offerId = packet.readInt();
        var catalogItem = page.getOffer(offerId);
        if (catalogItem == null)
            return;

        var extraData = packet.readString((short) 100);
        var amount = packet.readInt();
        if (amount < 1 || amount > 100)
            return;


        var purchaseHandler = catalogManager.getPurchaseHandlerForItem(catalogItem);
        if (purchaseHandler == null)
            throw new IllegalArgumentException("No purchase handlers were found. Missing implementation!");

        if (!purchaseHandler.canPurchase(client.getHabbo(), catalogItem, extraData, amount))
            return;

        purchaseHandler.purchase(client.getHabbo(), catalogItem, extraData, amount);
    }
}
