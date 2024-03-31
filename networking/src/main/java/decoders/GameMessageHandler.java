package decoders;

import com.google.inject.Inject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import networking.client.INitroClient;
import networking.client.INitroClientManager;
import networking.packets.IPacketManager;
import networking.packets.IncomingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.GameServerAttributes;

import java.io.IOException;

@ChannelHandler.Sharable
public class GameMessageHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();
    @Inject private INitroClientManager nitroClientManager;
    @Inject private IPacketManager packetManager;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        if (!nitroClientManager.tryAddClient(ctx)) {
            nitroClientManager.disconnectGuest(ctx);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        var message = (IncomingPacket) msg;

        try {
            var client = (INitroClient) ctx.attr(GameServerAttributes.CLIENT);
            if (client == null) {
                packetManager.ParseForGuest(message, ctx);
                return;
            }

            packetManager.Parse(message, client);
        } catch (Exception e) {
            logger.error("Caught exception", e);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
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