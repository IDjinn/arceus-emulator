package packets.incoming.guest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabboManager;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClientManager;
import networking.packets.IncomingPacket;
import networking.util.NoAuth;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

@Singleton
@NoAuth
public class SecureLoginEvent extends IncomingEvent {
    @Inject
    private IHabboManager userManager;
    @Inject
    private INitroClientManager clientManager;
   
    @Override
    public int getHeaderId() {
        return IncomingHeaders.SecureLoginEvent;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {
        var sso = packet.readString();
        var integer = packet.readInt();

        if (!userManager.tryLoginWithSSO(ctx, sso)) {
            clientManager.disconnectGuest(ctx);
            return;
        }
    }
}
