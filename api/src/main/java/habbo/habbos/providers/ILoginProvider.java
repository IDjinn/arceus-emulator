package habbo.habbos.providers;

import io.netty.channel.ChannelHandlerContext;

public interface ILoginProvider {
    boolean canLogin(ChannelHandlerContext ctx, String authTicket);

    void attemptLogin(ChannelHandlerContext ctx, String authTicket);
}
