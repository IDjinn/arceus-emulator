package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RoomMoveWallItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.MoveWallItemEvent;
    }

    @Override
    public void parse(final IncomingPacket packet, final INitroClient client) {
        if (client.getHabbo().getRoom() == null) return;

        final var itemId = packet.readInt();
        var item = client.getHabbo().getRoom().getObjectManager().getItemByVirtualId(itemId);
        if (!(item instanceof IWallItem wallItem)) return;

        final var coordinates = packet.readString();
        client.getHabbo().getRoom().getObjectManager().moveWallItemTo(client.getHabbo(), wallItem, coordinates);
    }
}
