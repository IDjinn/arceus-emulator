package decoders;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import networking.packets.IIncomingPacket;
import networking.packets.IPacketManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Singleton
@ChannelHandler.Sharable
public class IncomingPacketLogger extends MessageToMessageDecoder<IIncomingPacket> {
    @Inject private IPacketManager packetManager;
    private static final Logger logger = LogManager.getLogger();
    @Override
    protected void decode(ChannelHandlerContext ctx, IIncomingPacket packet, List<Object> out) {
        try {
            logger.debug("[-> incoming] {} packet {} [{}]",
                    packet.getHeader(),
                    this.packetManager.getIncomingEventName(packet.getHeader()),
                    packet.getBuffer());
        } catch (Exception e) {
            logger.error("error while decoding packet {}", packet.getHeader(), e);
        }

        out.add(packet);
    }
}
