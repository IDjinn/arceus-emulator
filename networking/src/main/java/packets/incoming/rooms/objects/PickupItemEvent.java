package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class PickupItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomPickupItemEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        if (client.getHabbo().getRoom() == null) return;

        // TODO ITEM OWNER
        if (!client.getHabbo().getRoom().getRightsManager().hasRights(client.getHabbo()))
            return;
        
        var isFloorItem = packet.readInt() == 2;
        var itemId = packet.readInt();
        var item = client.getHabbo().getRoom().getObjectManager().getItem(itemId);
        if (item == null) return;

        client.getHabbo().getRoom().getObjectManager().pickupItem(client.getHabbo(), item);
    }
}
