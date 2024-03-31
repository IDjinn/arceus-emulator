package packets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientManager;
import networking.packets.IPacketManager;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class PacketManager implements IPacketManager {
    private Logger logger = LogManager.getLogger();
    private final Executor sharedGuestExecutor = Executors.newSingleThreadExecutor();
    private final Executor packetManagerExecutor = Executors.newVirtualThreadPerTaskExecutor();
    @Inject private INitroClientManager clientManager;

    private final HashMap<Integer, IncomingEvent> incomingEvents = new HashMap<Integer, IncomingEvent>(){
        { }
    };
    private final HashMap<Integer, IncomingEvent> guestEvents = new HashMap<Integer, IncomingEvent>(){
        { }
    };
    
    @Override
    public String getIncomingEventName(int headerId) {
        return incomingEvents.get(headerId).getClass().getName();
    }

    @Override
    public String getOutgoingEventName(int headerId) {
        return null;
    }

    @Override
    public void Parse(IncomingPacket packet, INitroClient client) {
        var incomingEvent = incomingEvents.get(packet.getHeader());
        if (incomingEvent == null) {
            logger.debug("[-> incoming] {} was not found", packet.getHeader());
            return;
        }

        packetManagerExecutor.execute(() -> incomingEvent.Parse(packet, client));
    }

    @Override
    public void ParseForGuest(IncomingPacket packet, ChannelHandlerContext ctx) {
        try {
            var incomingEvent = guestEvents.get(packet.getHeader());
            if (incomingEvent == null) {
                clientManager.disconnectGuest(ctx);
                return;
            }

            sharedGuestExecutor.execute(() -> incomingEvent.ParseForGuest(packet, ctx));
        }
        catch (Exception e) {
            clientManager.disconnectGuest(ctx);
            logger.error(e.getMessage(), e);
        }
    }
}
