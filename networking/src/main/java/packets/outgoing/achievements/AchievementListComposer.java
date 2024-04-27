package packets.outgoing.achievements;

import habbo.habbos.IHabbo;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class AchievementListComposer extends OutgoingPacket {
    public AchievementListComposer(IHabbo habbo) {
        super(OutgoingHeaders.AchievementListComposer);
        appendInt(0);
    }
}
