package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import habbo.rooms.components.objects.items.wall.IWallItem;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RoomMoveWallItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.MoveWallItemEvent;
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null) return;

        if (!client.getHabbo().getEntity().hasRights())
            return;
        
        final var itemId = packet.readInt();
        var item = client.getHabbo().getRoom().getObjectManager().getItem(itemId);
        if (!(item instanceof IWallItem wallItem)) return;

        final var coordinates = packet.readString();
        client.getHabbo().getRoom().getObjectManager().moveWallItemTo(client.getHabbo(), wallItem, coordinates);
    }
}
