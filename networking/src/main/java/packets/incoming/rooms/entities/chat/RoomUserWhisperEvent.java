package packets.incoming.rooms.entities.chat;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
public class RoomUserWhisperEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserWhisperEvent;
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null || client.getHabbo().getPlayerEntity() == null) return;

        if (!client.getHabbo().getRoom().getRightsManager().canTalk(client.getHabbo().getPlayerEntity()))
            return;

        final var message = packet.readString();
        var username = "dfinn";
        final var bubble = packet.readInt(); // TODO WHISPER
//        
//        client.getHabbo().getRoom().getEntityManager().shout(
//                client.getHabbo().getPlayerEntity(),
//                message,
//                bubble
//        );
    }
}
