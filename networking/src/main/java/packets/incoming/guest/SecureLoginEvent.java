package packets.incoming.guest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.providers.ILoginProvider;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClientManager;
import networking.packets.IIncomingPacket;
import networking.util.NoAuth;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingHeaders;

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
    public void parseForGuest(IIncomingPacket packet, ChannelHandlerContext ctx) {
        String sso = packet.readString().replaceAll(" ", "");
        int integer = packet.readInt();

        if(!loginProvider.canLogin(ctx, sso)) {
            clientManager.disconnectGuest(ctx);
            return;
        }

        loginProvider.attemptLogin(ctx, sso);
    }
}
