package packets.dto.outgoing.badges;

import networking.packets.IPacketDTO;

import java.util.Map;

public record UserBadgesDTO(int userId, Map<Integer, String> badges) implements IPacketDTO {
}
