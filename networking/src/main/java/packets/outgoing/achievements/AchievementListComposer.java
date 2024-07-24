package packets.outgoing.achievements;

import habbo.habbos.IHabbo;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class AchievementListComposer extends OutgoingPacket {
    public AchievementListComposer(IHabbo habbo) {
        super(OutgoingHeaders.AchievementListComposer);
        this.appendInt(0);
    }
}
