package packets.dto.outgoing.inventory;

import habbo.habbos.data.badges.IHabboBadge;
import networking.packets.IPacketDTO;

import java.util.Map;

public record InventoryBadgesComposerDTO(Map<String, IHabboBadge> badges, Map<Integer, IHabboBadge> profileBadges) implements IPacketDTO {
}
