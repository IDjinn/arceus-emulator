package networking.packets.incoming.guest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbohotel.users.providers.ILoginProvider;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClientManager;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.IncomingHeaders;
import networking.util.NoAuth;

@Singleton
@NoAuth
public class SecureLoginEvent extends IncomingEvent {
    @Inject
    private INitroClientManager clientManager;

    @Inject
    private ILoginProvider loginProvider;
   
    @Override
    public int getHeaderId() {
        return IncomingHeaders.SecureLoginEvent;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {
        String sso = packet.readString().replaceAll(" ", "");
        var integer = packet.readInt();

        if(!loginProvider.canLogin(ctx, sso)) {
            clientManager.disconnectGuest(ctx);
            return;
        }

        loginProvider.attemptLogin(ctx, sso);
    }
}
