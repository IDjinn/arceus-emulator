package packets.incoming.rooms.entities.chat;

import com.google.inject.Singleton;
import habbo.GameErrors;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.incoming.rooms.entities.chat.assertion.ChatMessageStringAssertion;
import utils.result.GameError;
import utils.result.Result;

@Singleton
public class RoomUserTalkEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RoomUserTalkEvent;
    }

    @Override
    public Result<Boolean, GameError> validate(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getPlayerEntity() == null || client.getHabbo().getRoom() == null)
            return Result.error(GameErrors.Packets.Generic.MUST_BE_IN_ROOM);

        if (!client.getHabbo().getRoom().getRightsManager().canTalk(client.getHabbo().getPlayerEntity()))
            return Result.error(GameErrors.Packets.Room.Entities.CANNOT_TALK);

        return packet.assertion()
                .assertString(ChatMessageStringAssertion.instance, "message")
                .assertInteger(integer -> integer >= 0, "bubbleId")
                .result();
    }

    @Override
    public void parse(final IIncomingPacket packet, final IClient client) {
        if (client.getHabbo().getRoom() == null || client.getHabbo().getPlayerEntity() == null) return;

        if (!client.getHabbo().getRoom().getRightsManager().canTalk(client.getHabbo().getPlayerEntity()))
            return;

        final var message = packet.readString(); // TODO MESSAGE LENGTH LIMIT / FILTERING
        final var bubble = packet.readInt(); // TODO VERIFY THIS

        client.getHabbo().getRoom().getEntityManager().talk(
                client.getHabbo().getPlayerEntity(),
                message,
                bubble
        );
    }
}
