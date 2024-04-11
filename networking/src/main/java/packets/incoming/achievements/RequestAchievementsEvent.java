package packets.incoming.achievements;

import com.google.inject.Singleton;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
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
    public void parse(IIncomingPacket packet, IClient client) {
        client.sendMessage(new AchievementListComposer(client.getHabbo())); // TODO
    }
}
