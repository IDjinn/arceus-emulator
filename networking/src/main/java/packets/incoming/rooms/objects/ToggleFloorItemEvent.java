package packets.incoming.rooms.objects;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class ToggleFloorItemEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.ToggleFloorItemEvent;
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null || client.getHabbo().getPlayerEntity() == null) return;

        final var itemId = packet.readInt();
        final var state = packet.readInt();
        final var item = client.getHabbo().getRoom().getObjectManager().getFloorItem(itemId);
        if (item == null)
            return;

        if (!item.canUse(client.getHabbo().getPlayerEntity()) || !item.canInteract(client.getHabbo().getPlayerEntity()))
            return;

//        item.toggleState(state);
        item.onInteract(client.getHabbo().getPlayerEntity());
    }
}
