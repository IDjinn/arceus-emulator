package packets.incoming.rooms.entities.chat;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.rooms.entities.chat.RoomUserTypingComposer;

@Singleton
public class RoomUserStartTypingEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserStartTypingEvent;
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null || client.getHabbo().getEntity() == null) return;

        if (!client.getHabbo().getRoom().getRightsManager().canType(client.getHabbo().getEntity()))
            return;

        client.getHabbo().getRoom().broadcastMessage(
                new RoomUserTypingComposer(client.getHabbo().getEntity(),
                        true
                ));
    }
}
