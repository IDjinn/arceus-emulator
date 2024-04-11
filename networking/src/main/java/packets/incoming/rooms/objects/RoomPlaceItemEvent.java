package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

import java.util.Arrays;
import java.util.stream.Collectors;

@Singleton
public class RoomPlaceItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomPlaceItemEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        if (client.getHabbo().getRoom() == null) return;

        if (!client.getHabbo().getRoom().getRightsManager().hasRights(client.getHabbo()))
            return; // TODO: CHECK RIGHTS IN SPACE TO PLACE
        
        var data = packet.readString().split(" ");
        var itemId = Integer.parseInt(data[0]);
        var item = client.getHabbo().getInventory().getItem(itemId);
        if (item == null) return;

        switch (item.getFurniture().getType()) {
            case FLOOR -> {
                var x = Integer.parseInt(data[1]);
                var y = Integer.parseInt(data[2]);
                var rotation = Integer.parseInt(data[3]);

                client.getHabbo().getRoom().getObjectManager().placeFloorItem(client.getHabbo(), item, x, y, 0d, rotation);
            }
            case WALL ->
                    client.getHabbo().getRoom().getObjectManager().placeWallItem(client.getHabbo(), item, Arrays.stream(data).skip(1).collect(Collectors.joining(" ")));
            default -> throw new IllegalArgumentException("Item type is not valid");
        }
    }
}
