package networking.client;

import io.netty.channel.ChannelHandlerContext;

public interface INitroClientManager {
    void addClient(INitroClient client);

    boolean tryAddClient(ChannelHandlerContext ctx);

    void disconnectGuest(ChannelHandlerContext ctx);

    void dispose(INitroClient client);

    boolean hasLoggedHabboById(int habboId);
}
