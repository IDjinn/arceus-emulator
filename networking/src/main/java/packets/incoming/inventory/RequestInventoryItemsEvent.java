package packets.incoming.inventory;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
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
    public void parse(IIncomingPacket packet, INitroClient client) { // TODO INCOMING RATE LIMIT
        var allItems = client.getHabbo().getInventory().getItems().values().stream().toList();
        final var totalFragments = Math.max((int) Math.ceil(allItems.size() / InventoryPageSize), 1);
        for (int i = 1; i <= totalFragments; i++) {
            var pageItems = allItems;//.subList(Math.min(i * InventoryPageSize + InventoryPageSize, allItems.size()), Math.min(i * InventoryPageSize, allItems.size()));
            client.sendMessages(new InventoryItemsComposer(totalFragments, i, pageItems));
        }
    }
}
