package encoders;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import networking.packets.outgoing.IOutgoingDTOSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OutgoingPacketLogger extends MessageToMessageEncoder<IOutgoingDTOSerializer<U>> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void encode(ChannelHandlerContext ctx, IOutgoingDTOSerializer<U> message, List<Object> out) {
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