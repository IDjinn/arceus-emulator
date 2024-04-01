package networking.packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class RoomScoreComposer extends OutgoingPacket {
    public RoomScoreComposer(int score, boolean canVote) {
        super(OutgoingHeaders.RoomScoreComposer);
        appendInt(score);
        appendBoolean(canVote);
    }
}
