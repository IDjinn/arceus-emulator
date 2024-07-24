package packets.dto.outgoing.purse;

import networking.packets.IPacketDTO;

public record UserCreditsComposerDTO(int value) implements IPacketDTO {
}
