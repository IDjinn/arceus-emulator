package decoders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import networking.client.INitroClient;
import networking.client.INitroClientManager;
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
    @Inject private INitroClientManager nitroClientManager;
    @Inject private IPacketManager packetManager;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        if (!this.nitroClientManager.tryAddClient(ctx)) {
            this.nitroClientManager.disconnectGuest(ctx);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        this.nitroClientManager.disconnectGuest(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        var message = (IIncomingPacket) msg;

        try {
            var client = (INitroClient) ctx.attr(GameNetowrkingAttributes.CLIENT).get();
            if (client == null) {
                this.packetManager.parseForGuest(message, ctx);
                return;
            }

            this.packetManager.parse(message, client);
        } catch (Exception e) {
            logger.error("Caught exception", e);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.nitroClientManager.disconnectGuest(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            ctx.channel().close();
            return;
        }
        
        logger.error("Disconnecting client, exception in GameMessageHander.", cause);
        ctx.channel().close();
    }

}