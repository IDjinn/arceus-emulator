package client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import habbo.habbos.IHabboManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import networking.client.IClient;
import networking.client.IClientFactory;
import networking.client.IClientManager;
import networking.util.GameNetowrkingAttributes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ClientManager implements IClientManager {
    private final Map<ChannelId, Channel> guests;
    private final Map<ChannelId, IClient> clients;

    @Inject
    private IHabboManager habboManager;

    @Inject
    private IClientFactory clientFactory;

    public ClientManager() {
        this.guests = new ConcurrentHashMap<>();
        this.clients = new ConcurrentHashMap<>();
    }

    @Override
    public void addClient(IClient client) {
        this.clients.put(client.getContext().channel().id(), client);
    }

    @Override
    public boolean tryAddClient(ChannelHandlerContext ctx) {
        ctx.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> disconnectGuest(ctx));
        ctx.fireChannelRegistered();

        return this.guests.putIfAbsent(ctx.channel().id(), ctx.channel()) == null;
    }

    @Override
    public void onDisconnect(final ChannelHandlerContext ctx) {
        if (ctx.hasAttr(GameNetowrkingAttributes.CLIENT)) {
            this.dispose(ctx.attr(GameNetowrkingAttributes.CLIENT).get());
        } else {
            this.disconnectGuest(ctx);
        }
    }

    @Override
    public void disconnectGuest(ChannelHandlerContext ctx) {
        this.guests.remove(ctx.channel().id());
        ctx.disconnect();
    }

    @Override
    public boolean hasLoggedHabboById(int habboId) {
        return this.clients.values().stream().anyMatch(client -> client.getHabbo().getData().getId() == habboId);
    }

    @Override
    public void dispose(IClient client) {
        this.habboManager.onLogoff(client.getHabbo());
        this.clients.remove(client.getContext().channel().id());
        client.dispose();
    }
}
