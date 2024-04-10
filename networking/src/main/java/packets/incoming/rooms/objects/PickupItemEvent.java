package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class PickupItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomPickupItemEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        if (client.getHabbo().getRoom() == null) return;

        var isFloorItem = packet.readInt() == 2;
        var itemId = packet.readInt();
        var item = client.getHabbo().getRoom().getObjectManager().getItemByVirtualId(itemId);
        if (item == null) return;

        client.getHabbo().getRoom().getObjectManager().pickupItem(client.getHabbo(), item);
    }
}
