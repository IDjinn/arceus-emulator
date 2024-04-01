package networking.packets.outgoing.session.logindata;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class UserAchievementScoreComposer extends OutgoingPacket {
    public UserAchievementScoreComposer() {
        super(OutgoingHeaders.UserAchievementScoreComposer);
        appendInt(100);
    }
}
