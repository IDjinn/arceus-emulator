package networking.packets.incoming.achievements;

import com.google.inject.Singleton;
import networking.client.INitroClient;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.packets.outgoing.achievements.AchievementListComposer;

@Singleton
public class RequestAchievementsEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestAchievementsEvent;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        client.sendMessage(new AchievementListComposer(client.getHabbo())); // TODO
    }
}
