package networking.packets.outgoing.achievements;

import habbohotel.users.IHabbo;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class AchievementListComposer extends OutgoingPacket {
    public AchievementListComposer(IHabbo habbo) {
        super(OutgoingHeaders.AchievementListComposer);
        appendInt(0);
    }
}
