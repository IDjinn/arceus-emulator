package habbo.habbos;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClientManager;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import packets.outgoing.PingComposer;
import packets.outgoing.guest.SecureLoginOkComposer;
import packets.outgoing.session.CfhTopicsMessageComposer;
import packets.outgoing.session.MysteryBoxKeysComposer;
import packets.outgoing.session.buildersclub.BuildersClubExpiredComposer;
import packets.outgoing.session.calendar.AdventCalendarDataComposer;
import packets.outgoing.session.habboclub.UserClubComposer;
import packets.outgoing.session.hotel.AvailabilityStatusMessageComposer;
import packets.outgoing.session.inventory.InventoryAchievementsComposer;
import packets.outgoing.session.inventory.InventoryRefreshComposer;
import packets.outgoing.session.logindata.*;
import packets.outgoing.session.rooms.FavoriteRoomsCountComposer;
import packets.outgoing.session.rooms.UserHomeRoomComposer;
import packets.outgoing.session.wardobe.UserClothesComposer;

import java.util.ArrayList;


@Singleton
public class HabboManager implements IHabboManager {
    @Inject
    private INitroClientManager clientManager;
    @Override
    public boolean tryLoginWithSSO(@NotNull ChannelHandlerContext ctx, @NotNull String sso) {
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
