package packets;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientManager;
import networking.packets.IPacketManager;
import networking.packets.IncomingPacket;
import networking.packets.incoming.IncomingEvent;
import networking.packets.incoming.guest.SecureLoginEvent;
import networking.util.NoAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class PacketManager implements IPacketManager {
    private Logger logger = LogManager.getLogger();
    private final Executor sharedGuestExecutor = Executors.newSingleThreadExecutor();
    private final Executor packetManagerExecutor = Executors.newVirtualThreadPerTaskExecutor();
    @Inject private INitroClientManager clientManager;

    private final HashMap<Integer, IncomingEvent> incomingEvents = new HashMap<Integer, IncomingEvent>();
    private final HashMap<Integer, IncomingEvent> guestEvents = new HashMap<Integer, IncomingEvent>();

    @Inject
    private SecureLoginEvent test;

    @Inject
    public PacketManager(INitroClientManager clientManager, List<Class<? extends IncomingEvent>> incomings, Injector injector) {
        this.clientManager = clientManager;

        for (Class<? extends IncomingEvent> incoming : incomings) {
            if (incoming.isAnnotationPresent(NoAuth.class)) registerGuestEvent(injector.getProvider(incoming).get());
            else registerIncomingEvent(injector.getProvider(incoming).get());
        }
    }

    private void registerIncomingEvent(IncomingEvent incomingEvent) {
        incomingEvents.put(incomingEvent.getHeaderId(), incomingEvent);
    }


    private void registerGuestEvent(IncomingEvent event) {
        guestEvents.put(event.getHeaderId(), event);
    }
    
    @Override
    public String getIncomingEventName(int headerId) {
        if (incomingEvents.containsKey(headerId))
            return incomingEvents.get(headerId).getClass().getName();

        if (guestEvents.containsKey(headerId))
            return guestEvents.get(headerId).getClass().getName();

        return "Unknown";
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
