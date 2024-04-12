package packets.incoming.rooms.entities.chat;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.rooms.entities.chat.RoomUserTypingComposer;

@Singleton
public class RoomUserStopTypingEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserStopTypingEvent;
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null || client.getHabbo().getPlayerEntity() == null) return;

        if (!client.getHabbo().getRoom().getRightsManager().canType(client.getHabbo().getPlayerEntity()))
            return;

        client.getHabbo().getRoom().broadcastMessage(
                new RoomUserTypingComposer(client.getHabbo().getPlayerEntity(),
                        false
                ));
    }
}
