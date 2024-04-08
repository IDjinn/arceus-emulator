package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RoomPlaceItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomPlaceItemEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        if (client.getHabbo().getRoom() == null) return;

        var data = packet.readString().split(" ");
        var itemId = Integer.parseInt(data[0]);
        var item = client.getHabbo().getInventory().getItem(itemId);
        if (item == null) return;

        // TODO CHECK RIGHTS
        switch (item.getFurniture().getType()) {
            case FLOOR -> {
                var x = Integer.parseInt(data[1]);
                var y = Integer.parseInt(data[2]);
                var rotation = Integer.parseInt(data[3]);

                client.getHabbo().getRoom().getObjectManager().placeFloorItem(item, x, y, 0d, rotation);
            }
            case WALL -> {

            }
            default -> throw new IllegalArgumentException("Item type is not valid");
        }
    }
}
