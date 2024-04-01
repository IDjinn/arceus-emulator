package client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import networking.client.INitroClient;
import networking.client.INitroClientFactory;
import networking.client.INitroClientManager;
import networking.util.GameServerAttributes;

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class NitroClientManager implements INitroClientManager {
    private final ConcurrentHashMap<ChannelId, Channel>  guests = new ConcurrentHashMap<ChannelId, Channel>();
    private final ConcurrentHashMap<ChannelId, INitroClient> clients = new ConcurrentHashMap<ChannelId, INitroClient>();

    @Inject
    private INitroClientFactory clientFactory;
    @Override
    public INitroClient clientHandshakeFinished(ChannelHandlerContext ctx) {
        var client = this.clientFactory.create(ctx);
        this.clients.put(ctx.channel().id(), client);
        ctx.attr(GameServerAttributes.CLIENT).set(client);
        return client;
    }

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
