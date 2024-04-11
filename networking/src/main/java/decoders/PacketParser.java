package decoders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import networking.client.IClient;
import networking.client.IClientManager;
import networking.packets.IIncomingPacket;
import networking.packets.IPacketManager;
import networking.util.GameNetowrkingAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@Singleton
@ChannelHandler.Sharable
public class PacketParser extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();
    @Inject
    private IClientManager clientManager;
    @Inject private IPacketManager packetManager;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        if (!this.clientManager.tryAddClient(ctx)) {
            this.clientManager.disconnectGuest(ctx);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        this.clientManager.onDisconnect(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        var message = (IIncomingPacket) msg;

        try {
            var client = (IClient) ctx.attr(GameNetowrkingAttributes.CLIENT).get();
            if (client == null) {
                this.packetManager.parseForGuest(message, ctx);
                return;
            }

            this.packetManager.parse(message, client);
        } catch (Exception e) {
            logger.error("Caught exception", e);
        }
    }

//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        this.clientManager.onDisconnect(ctx);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.clientManager.onDisconnect(ctx);
        if (cause instanceof IOException) 
            return;

        logger.error("Disconnecting client, exception in PacketParser.", cause);
    }
}