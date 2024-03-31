package networking.client;

import io.netty.channel.ChannelHandlerContext;

public interface INitroClientManager {
    public boolean tryAddClient(ChannelHandlerContext ctx);
    public void disconnectGuest(ChannelHandlerContext ctx);
    public void dispose(INitroClient client);
}
