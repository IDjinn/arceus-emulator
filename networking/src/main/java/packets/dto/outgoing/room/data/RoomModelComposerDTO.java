package packets.dto.outgoing.room.data;

import networking.packets.IPacketDTO;

public record RoomModelComposerDTO(String modelName, int roomId) implements IPacketDTO {
}
