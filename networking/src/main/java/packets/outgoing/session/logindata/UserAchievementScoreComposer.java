package packets.outgoing.session.logindata;

import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

public class UserAchievementScoreComposer extends OutgoingPacket {
    public UserAchievementScoreComposer() {
        super(OutgoingHeaders.UserAchievementScoreComposer);
        appendInt(100);
    }
}
