package packets.incoming.inventory;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.inventory.InventoryItemsComposer;

@Singleton
public class RequestInventoryItemsEvent extends IncomingEvent {
    private static final int InventoryPageSize = 50;

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestInventoryItemsEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) { // TODO INCOMING RATE LIMIT
        var allItems = client.getHabbo().getInventory().getItems().values().stream().toList();
        final var pageCount = (int) Math.ceil(allItems.size() / InventoryPageSize);
        for (int i = 1; i <= pageCount; i++) {
            var pageItems = allItems.subList(i * InventoryPageSize, Math.min(i * InventoryPageSize + InventoryPageSize, allItems.size()));
            client.sendMessages(new InventoryItemsComposer(pageCount, i, pageItems));
        }
    }
}
