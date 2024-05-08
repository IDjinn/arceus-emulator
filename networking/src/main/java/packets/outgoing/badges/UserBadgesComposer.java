package packets.outgoing.badges;

import habbo.habbos.data.badges.IHabboBadge;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

import java.util.Map;

public class UserBadgesComposer extends OutgoingPacket {
    public UserBadgesComposer(final Map<Integer, String> profileBadges, int userId) {
        super(OutgoingHeaders.UserBadgesComposer);
        this.appendInt(userId);
        profileBadges.forEach((slot, code) -> {
            this.appendInt(slot);
            this.appendString(code);
        });
    }

    public UserBadgesComposer(final int userId, final Map<Integer, IHabboBadge> profileBadges) {
        super(OutgoingHeaders.UserBadgesComposer);
        this.appendInt(userId);
        UserBadgesComposer.serialize(this, profileBadges);
    }

    public static void serialize(final OutgoingPacket packet, final Map<Integer, IHabboBadge> profileBadges) {
        packet.appendInt(profileBadges.size());
        for (IHabboBadge badge : profileBadges.values()) {
            packet.appendInt(badge.getSlot().orElse(0));
            packet.appendString(badge.getCode());
        }
    }
}
