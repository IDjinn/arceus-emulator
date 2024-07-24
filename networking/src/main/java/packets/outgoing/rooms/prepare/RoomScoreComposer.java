package packets.outgoing.rooms.prepare;

import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class RoomScoreComposer extends OutgoingPacket {
    public RoomScoreComposer(int score, boolean canVote) {
        super(OutgoingHeaders.RoomScoreComposer);
        this.appendInt(score);
        this.appendBoolean(canVote);
    }
}
