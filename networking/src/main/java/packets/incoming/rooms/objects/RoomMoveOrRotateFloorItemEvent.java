package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import habbo.rooms.components.objects.items.floor.IFloorItem;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import utils.Position;

@Singleton
public class RoomMoveOrRotateFloorItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomMoveOrRotateFloorItemEvent;
    }

    @Override
    public void parse(final IncomingPacket packet, final INitroClient client) {
        if (client.getHabbo().getRoom() == null) return;

        final var itemId = packet.readInt();
        var item = client.getHabbo().getRoom().getObjectManager().getItemByVirtualId(itemId);
        if (!(item instanceof IFloorItem floorItem)) return;

        final var x = packet.readInt();
        final var y = packet.readInt();
        final var rotation = packet.readInt();

        client.getHabbo().getRoom().getObjectManager().moveFloorItemTo(client.getHabbo(), floorItem, new Position(x, y),
                rotation);
    }
}
