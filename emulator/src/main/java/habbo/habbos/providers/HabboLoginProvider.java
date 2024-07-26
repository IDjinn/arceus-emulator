package habbo.habbos.providers;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.habbos.IHabboManager;
import habbo.habbos.factories.IHabboFactory;
import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.client.IClientFactory;
import networking.client.IClientManager;
import networking.util.GameNetowrkingAttributes;
import packets.dto.outgoing.guest.SecureLoginOkComposerDTO;
import packets.dto.outgoing.inventory.InventoryAchievementsComposerDTO;
import packets.dto.outgoing.inventory.InventoryRefreshComposerDTO;
import packets.dto.outgoing.session.PingComposerDTO;
import packets.dto.outgoing.session.buildersclub.BuildersClubExpiredComposerDTO;
import packets.dto.outgoing.session.calendar.AdventCalendarDataComposerDTO;
import packets.dto.outgoing.session.habboclub.UserClubComposerDTO;
import packets.dto.outgoing.session.hotel.AvailabilityStatusMessageComposerDTO;
import packets.dto.outgoing.session.logindata.*;
import packets.dto.outgoing.session.wardobe.UserClothesComposerDTO;
import storage.repositories.habbo.IHabboRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class HabboLoginProvider implements ILoginProvider {
    @Inject
    private IHabboRepository habboRepository;

    @Inject
    private IClientManager clientManager;

    @Inject
    private IClientFactory clientFactory;

    @Inject
    private IHabboFactory habboFactory;

    @Inject
    private IHabboManager habboManager;

    @Override
    public boolean canLogin(ChannelHandlerContext ctx, String authTicket) {
        if(authTicket.isEmpty()) {
            return false;
        }

        final AtomicInteger habboId = new AtomicInteger(-1);

        this.habboRepository.getHabboIdByAuthTicket(consumer -> {
            if(consumer == null) return;

            habboId.set(consumer.getInt("id"));
        }, authTicket);

        if(habboId.get() <= 0) {
            return false;
        }

        return !this.clientManager.hasLoggedHabboById(habboId.get());
    }

    public void attemptLogin(ChannelHandlerContext ctx, String authTicket) {
        this.habboRepository.getHabboDataByAuthTicket(result -> {
            if(result == null) {
                this.clientManager.disconnectGuest(ctx);
                return;
            }

            IClient client = this.clientFactory.create(ctx);
            ctx.attr(GameNetowrkingAttributes.CLIENT).set(client);

            final IHabbo habbo = this.habboFactory.create(client, result);
            this.habboManager.onLogin(habbo);

            client.setHabbo(habbo);
            this.clientManager.addClient(client);

            this.sendLoginPackets(client);
        }, authTicket);
    }

    private void sendLoginPackets(IClient client) {
        client.sendMessages(
                new SecureLoginOkComposerDTO()
                , new UserEffectsListComposerDTO()
                , new UserClothesComposerDTO()
                , new NewUserIdentityComposerDTO()
                , new UserPermissionsComposerDTO()
                , new AvailabilityStatusMessageComposerDTO()
                , new PingComposerDTO()
                , new EnableNotificationsComposerDTO()
                , new UserAchievementScoreComposerDTO()
                , new IsFirstLoginOfDayComposerDTO()
//                , new MysteryBoxKeysComposerDTO()
                , new BuildersClubExpiredComposerDTO()
//                , new CfhTopicsMessageComposerDTO()
//                , new FavoriteRoomsCountComposerDTO()
                , new AdventCalendarDataComposerDTO()
                , new UserClubComposerDTO()
                , new InventoryRefreshComposerDTO()
                , new InventoryAchievementsComposerDTO()
//                , new UserHomeRoomComposerDTO(0, 0)
        );
    }
}
