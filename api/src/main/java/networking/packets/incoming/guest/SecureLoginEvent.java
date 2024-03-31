package networking.packets.incoming.guest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbohotel.users.IUserManager;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClientManager;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.util.NoAuth;

@Singleton
@NoAuth
public class SecureLoginEvent extends IncomingEvent {
    @Inject
    private IUserManager userManager;
    @Inject
    private INitroClientManager clientManager;
   
    @Override
    public int getHeaderId() {
        return 2419;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {
        var sso = packet.readString();
        var integer = packet.readInt();

        if (!userManager.tryLoginWithSSO(sso)) {
            clientManager.disconnectGuest(ctx);
            return;
        }
    }
}
