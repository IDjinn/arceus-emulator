package habbo.habbos.providers;

import com.google.inject.Inject;
import habbo.habbos.IHabbo;
import habbo.habbos.factories.IHabboFactory;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientFactory;
import networking.client.INitroClientManager;
import networking.util.GameNetowrkingAttributes;
import packets.outgoing.PingComposer;
import packets.outgoing.guest.SecureLoginOkComposer;
import packets.outgoing.inventory.InventoryAchievementsComposer;
import packets.outgoing.inventory.InventoryRefreshComposer;
import packets.outgoing.session.CfhTopicsMessageComposer;
import packets.outgoing.session.MysteryBoxKeysComposer;
import packets.outgoing.session.buildersclub.BuildersClubExpiredComposer;
import packets.outgoing.session.calendar.AdventCalendarDataComposer;
import packets.outgoing.session.habboclub.UserClubComposer;
import packets.outgoing.session.hotel.AvailabilityStatusMessageComposer;
import packets.outgoing.session.logindata.*;
import packets.outgoing.session.rooms.FavoriteRoomsCountComposer;
import packets.outgoing.session.rooms.UserHomeRoomComposer;
import packets.outgoing.session.wardobe.UserClothesComposer;
import storage.repositories.habbo.IHabboRepository;

import java.util.concurrent.atomic.AtomicInteger;

public class HabboLoginProvider implements ILoginProvider {
    @Inject
    private IHabboRepository habboRepository;

    @Inject
    private INitroClientManager clientManager;

    @Inject
    private INitroClientFactory clientFactory;

    @Inject
    private IHabboFactory habboFactory;

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

            INitroClient client = this.clientFactory.create(ctx);
            ctx.attr(GameNetowrkingAttributes.CLIENT).set(client);

            final IHabbo habbo = this.habboFactory.create(client, result);

            client.setHabbo(habbo);
            this.clientManager.addClient(client);

            this.sendLoginPackets(client);
        }, authTicket);
    }

    private void sendLoginPackets(INitroClient client) {
        client.sendMessages(new SecureLoginOkComposer()
                , new UserEffectsListComposer()
                , new UserClothesComposer()
                , new NewUserIdentityComposer()
                , new UserPermissionsComposer()
                , new AvailabilityStatusMessageComposer()
                , new PingComposer()
                , new EnableNotificationsComposer()
                , new UserAchievementScoreComposer()
                , new IsFirstLoginOfDayComposer()
                , new MysteryBoxKeysComposer()
                , new BuildersClubExpiredComposer()
                , new CfhTopicsMessageComposer()
                , new FavoriteRoomsCountComposer()
                , new AdventCalendarDataComposer()
                , new UserClubComposer()
                , new InventoryRefreshComposer()
                , new InventoryAchievementsComposer()
                , new UserHomeRoomComposer(0, 0));
    }
}
