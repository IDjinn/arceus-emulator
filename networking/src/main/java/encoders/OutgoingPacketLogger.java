package encoders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import networking.packets.OutgoingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OutgoingPacketLogger extends MessageToMessageEncoder<OutgoingPacket> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void encode(ChannelHandlerContext ctx, OutgoingPacket message, List<Object> out) {
        try {
            logger.debug("[-> outgoing] {} packet {} [{}",
                    message.getHeader(),
                    message.getClass().getName(),
                    message.getBuffer());
        } catch (Exception e) {
            logger.error("error at outgoing packet {}", message.getClass().getName(), e);
        }
        out.add(message);
    }

}