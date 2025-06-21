package packets.incoming.rooms.entities.chat;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RoomUserShoutEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserShoutEvent;
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null || client.getHabbo().getEntity() == null) return;

        if (!client.getHabbo().getRoom().getRightsManager().canTalk(client.getHabbo().getEntity()))
            return;

        final var message = packet.readString(); // TODO MESSAGE LENGTH LIMIT / FILTERING
        final var bubble = packet.readInt(); // TODO VERIFY THIS

        client.getHabbo().getRoom().getChatComponent().shout(
                client.getHabbo().getEntity(),
                message,
                bubble
        );
    }
}
