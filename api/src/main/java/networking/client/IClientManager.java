package networking.client;

import io.netty.channel.ChannelHandlerContext;

public interface IClientManager {
    void addClient(IClient client);

    boolean tryAddClient(ChannelHandlerContext ctx);

    void onDisconnect(ChannelHandlerContext ctx);

    void disconnectGuest(ChannelHandlerContext ctx);

    void dispose(IClient client);

    boolean hasLoggedHabboById(int habboId);
}
