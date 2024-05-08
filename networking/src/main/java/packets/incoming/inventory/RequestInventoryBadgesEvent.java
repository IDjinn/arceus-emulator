package packets.incoming.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabboManager;
import networking.client.IClient;
import networking.packets.IIncomingPacket;
import networking.packets.IncomingEvent;
import packets.incoming.IncomingHeaders;
import packets.outgoing.badges.UserBadgesComposer;
import packets.outgoing.inventory.InventoryBadgesComposer;
import storage.repositories.habbo.IHabboBadgesRepository;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class RequestInventoryBadgesEvent extends IncomingEvent {
    @Inject
    IHabboManager habboManager;
    @Inject
    IHabboBadgesRepository badgesRepository;

    @Override
    public int getHeaderId() {
        return IncomingHeaders.RequestInventoryBadgesEvent;
    }

    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        if (packet.getReadableBytes() == 0) {
            client.sendMessages(new InventoryBadgesComposer(
                    client.getHabbo().getBadgesComponent().getBadges(),
                    client.getHabbo().getBadgesComponent().getProfileEquippedBadges()
            ));
            return;
        }

        final int userId = packet.readInt();
        if (this.habboManager.isOnline(userId)) {
            final var target = this.habboManager.getOnlineHabbo(userId).get();
            client.sendMessage(new UserBadgesComposer(userId, target.getBadgesComponent().getProfileEquippedBadges()));
            return;
        }

        // TODO IMPLEMENTATION ELSEWHERE TO CACHING ETC
        final Map<Integer, String> equippedBadges = new HashMap<Integer, String>();
        this.badgesRepository.getEquippedBadges(result -> {
            if (result == null) return;

            equippedBadges.put(result.getInt("slot_id"), result.getString("badge_code"));
        }, userId);
        client.sendMessage(new UserBadgesComposer(equippedBadges, userId));
    }
}
