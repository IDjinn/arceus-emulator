package habbohotel.users;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
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
import org.jetbrains.annotations.NotNull;
import storage.repositories.users.IUserRepository;

import java.util.ArrayList;


@Singleton
public class HabboManager implements IHabboManager {
    @Inject
    private IUserRepository userRepository;

    @Inject
    private INitroClientManager clientManager;

    @Override
    public boolean tryLoginWithSSO(@NotNull ChannelHandlerContext ctx, @NotNull String sso) {
        userRepository.getUserByAuthTicket(sso);

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
            ctx.channel().write(message.getBuffer());
        }
        ctx.channel().flush();

        clientManager.clientHandshakeFinished(ctx);
        return true;
    }
}
