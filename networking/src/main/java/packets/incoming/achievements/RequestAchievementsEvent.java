package packets.incoming.achievements;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.achievements.AchievementListComposer;

@Singleton
public class RequestAchievementsEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestAchievementsEvent;
    }

    @Override
    public void parse(IncomingPacket packet, INitroClient client) {
        client.sendMessage(new AchievementListComposer(client.getHabbo())); // TODO
    }
}
