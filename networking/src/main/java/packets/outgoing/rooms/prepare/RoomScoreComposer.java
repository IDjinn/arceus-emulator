package packets.outgoing.rooms.prepare;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class RoomScoreComposer extends OutgoingPacket {
    public RoomScoreComposer(int score, boolean canVote) {
        super(OutgoingHeaders.RoomScoreComposer);
        appendInt(score);
        appendBoolean(canVote);
    }
}
