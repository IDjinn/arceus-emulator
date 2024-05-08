package packets.outgoing.inventory;

import habbo.habbos.data.badges.IHabboBadge;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.badges.UserBadgesComposer;

import java.util.Map;

public class InventoryBadgesComposer extends OutgoingPacket {
    public InventoryBadgesComposer(Map<String, IHabboBadge> badges, Map<Integer, IHabboBadge> profileBadges) {
        super(OutgoingHeaders.InventoryBadgesComposer);

        this.appendInt(badges.size());
        for (IHabboBadge badge : badges.values()) {
            this.appendInt(badge.getSlot().orElse(0));
            this.appendString(badge.getCode());
        }

        UserBadgesComposer.serialize(this, profileBadges);
    }
}
