package client;

import com.google.inject.Singleton;
import io.netty.channel.*;
import networking.client.INitroClient;
import networking.client.INitroClientManager;
import util.GameServerAttributes;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class NitroClientManager implements INitroClientManager {
    private final ConcurrentHashMap<ChannelId, Channel>  guests = new ConcurrentHashMap<ChannelId, Channel>();
    private final ConcurrentHashMap<ChannelId, INitroClient> clients = new ConcurrentHashMap<ChannelId, INitroClient>();
    @Override
    public boolean tryAddClient(ChannelHandlerContext ctx) {
        ctx.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> disconnectGuest(ctx));
        ctx.fireChannelRegistered();
        return this.guests.putIfAbsent(ctx.channel().id(), ctx.channel()) == null;
    }

    @Override
    public void disconnectGuest(ChannelHandlerContext ctx) {
        this.guests.remove(ctx.channel().id());
        ctx.disconnect();
    }

    @Override
    public void dispose(INitroClient client) {
        // TODO
    }
}
