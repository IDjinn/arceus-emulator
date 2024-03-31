package networking.packets.incoming.guest;

import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.util.GameServerAttributes;
import networking.util.NoAuth;
import networking.util.ReleaseVersion;

@Singleton
@NoAuth
public class ReleaseVersionEvent extends IncomingEvent {
    @Override
    public int getHeaderId() {
        return 4000;
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {
        var production = packet.readString();
        var type = packet.readString();
        var platform = packet.readInt();
        var category = packet.readInt();

        ctx.attr(GameServerAttributes.RELEASE_VERSION).set(new ReleaseVersion(production, type, platform, category));
    }
}
