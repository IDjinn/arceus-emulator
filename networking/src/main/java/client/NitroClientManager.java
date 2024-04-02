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

import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class NitroClientManager implements INitroClientManager {
    private final ConcurrentHashMap<ChannelId, Channel>  guests = new ConcurrentHashMap<ChannelId, Channel>();
    private final ConcurrentHashMap<ChannelId, INitroClient> clients = new ConcurrentHashMap<ChannelId, INitroClient>();

    @Inject
    private INitroClientFactory clientFactory;

    @Override
    public void addClient(INitroClient client) {
        this.clients.put(client.getContext().channel().id(), client);
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
    public boolean hasLoggedHabboById(int habboId) {
        return this.clients.values().stream().anyMatch(client -> client.getHabbo().getId() == habboId);
    }

    @Override
    public void dispose(INitroClient client) {
        // TODO
    }
}
