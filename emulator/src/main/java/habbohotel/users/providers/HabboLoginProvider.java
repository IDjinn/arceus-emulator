package habbohotel.users.providers;

import com.google.inject.Inject;
import habbohotel.users.IHabbo;
import habbohotel.users.IHabboFactory;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientFactory;
import networking.client.INitroClientManager;
import networking.packets.OutgoingPacket;
import networking.packets.outgoing.PingComposer;
import networking.packets.outgoing.guest.SecureLoginOkComposer;
import networking.packets.outgoing.session.CfhTopicsMessageComposer;
import networking.packets.outgoing.session.MysteryBoxKeysComposer;
import networking.packets.outgoing.session.buildersclub.BuildersClubExpiredComposer;
import networking.packets.outgoing.session.calendar.AdventCalendarDataComposer;
import networking.packets.outgoing.session.habboclub.UserClubComposer;
import networking.packets.outgoing.session.hotel.AvailabilityStatusMessageComposer;
import networking.packets.outgoing.session.inventory.InventoryAchievementsComposer;
import networking.packets.outgoing.session.inventory.InventoryRefreshComposer;
import networking.packets.outgoing.session.logindata.*;
import networking.packets.outgoing.session.rooms.FavoriteRoomsCountComposer;
import networking.packets.outgoing.session.rooms.UserHomeRoomComposer;
import networking.packets.outgoing.session.wardobe.UserClothesComposer;
import networking.util.GameServerAttributes;
import storage.repositories.habbo.IHabboRepository;
import storage.results.IConnectionResult;

import java.util.ArrayList;

public class HabboLoginProvider implements ILoginProvider {
    @Inject
    private IHabboRepository userRepository;

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

        int habboId = userRepository.getHabboIdByAuthTicket(authTicket);

        if(habboId <= 0) {
            return false;
        }

        return !clientManager.hasLoggedHabboById(habboId);
    }

    public void attemptLogin(ChannelHandlerContext ctx, String authTicket) {
        final IConnectionResult habboData = userRepository.getHabboDataByAuthTicket(authTicket);

        if(habboData == null) {
            clientManager.disconnectGuest(ctx);
            return;
        }

        INitroClient client = clientFactory.create(ctx);
        ctx.attr(GameServerAttributes.CLIENT).set(client);

        final IHabbo habbo = habboFactory.create(client, habboData);

        client.setHabbo(habbo);
        clientManager.addClient(client);

        this.sendLoginPackets(client);
    }

    private void sendLoginPackets(INitroClient client) {
        ArrayList<OutgoingPacket> messages = new ArrayList<>();

        messages.add(new SecureLoginOkComposer());
        messages.add(new UserEffectsListComposer());
        messages.add(new UserClothesComposer());
        messages.add(new NewUserIdentityComposer());
        messages.add(new UserPermissionsComposer());
        messages.add(new AvailabilityStatusMessageComposer());
        messages.add(new PingComposer());
        messages.add(new EnableNotificationsComposer());
        messages.add(new UserAchievementScoreComposer());
        messages.add(new IsFirstLoginOfDayComposer());
        messages.add(new MysteryBoxKeysComposer());
        messages.add(new BuildersClubExpiredComposer());
        messages.add(new CfhTopicsMessageComposer());
        messages.add(new FavoriteRoomsCountComposer());
        messages.add(new AdventCalendarDataComposer());
        messages.add(new UserClubComposer());
        messages.add(new InventoryRefreshComposer());
        messages.add(new InventoryAchievementsComposer());
        messages.add(new UserHomeRoomComposer(0, 0));

        for (var message : messages) {
            client.getContext().channel().write(message.getBuffer());
        }

        client.getContext().channel().flush();
    }
}
