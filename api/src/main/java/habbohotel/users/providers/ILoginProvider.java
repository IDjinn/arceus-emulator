package habbohotel.users.providers;

import io.netty.channel.ChannelHandlerContext;

public interface ILoginProvider {
    public boolean canLogin(ChannelHandlerContext ctx, String authTicket);

    public void attemptLogin(ChannelHandlerContext ctx, String authTicket);
}
