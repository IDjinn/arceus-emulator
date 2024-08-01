package packets.dto.outgoing.room.data;

import networking.packets.IPacketDTO;

public record RoomScoreComposerDTO(int score, boolean canVote) implements IPacketDTO {
}
