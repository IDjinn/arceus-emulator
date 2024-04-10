package packets;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import core.IThreadManager;
import core.configuration.IConfigurationManager;
import io.netty.channel.ChannelHandlerContext;
import networking.client.INitroClient;
import networking.client.INitroClientManager;
import networking.packets.IIncomingPacket;
import networking.packets.IPacketManager;
import networking.util.NoAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.incoming.IncomingEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class PacketManager implements IPacketManager {
    private Logger logger = LogManager.getLogger();
    private final Executor sharedGuestExecutor = Executors.newSingleThreadExecutor();
    private final INitroClientManager clientManager;
    private final IConfigurationManager configuration;
    private final IThreadManager threadManager;
    
    private final HashMap<Integer, IncomingEvent> incomingEvents = new HashMap<Integer, IncomingEvent>();
    private final HashMap<Integer, IncomingEvent> guestEvents = new HashMap<Integer, IncomingEvent>();

    @Inject
    public PacketManager(INitroClientManager clientManager, List<Class<? extends IncomingEvent>> incomings, Injector injector, IConfigurationManager configuration, IThreadManager threadManager) {
        this.clientManager = clientManager;
        this.configuration = configuration;
        this.threadManager = threadManager;

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
    public boolean isParallelParsingEnabled() {
        return this.configuration.getBool("io.parallel.packet.enabled", false);
    }

    @Override
    public boolean isLoggingEnabled() {
        return this.configuration.getBool("io.logging.enabled", false);
    }

    @Override
    public String getIncomingEventName(int headerId) {
        if (incomingEvents.containsKey(headerId))
            return incomingEvents.get(headerId).getClass().getName();

        if (guestEvents.containsKey(headerId))
            return guestEvents.get(headerId).getClass().getName();

        return "Unknown";
    }

    private final HashSet<Integer> notFoundPackets = new HashSet<Integer>();
    @Override
    public void parse(IIncomingPacket packet, INitroClient client) {
        var incomingEvent = incomingEvents.get(packet.getHeader());
        if (incomingEvent == null) {
            if (notFoundPackets.add(packet.getHeader()))
                logger.warn("[-> incoming] {} was not found", packet.getHeader());
            return;
        }

        if (this.isParallelParsingEnabled()) {
            this.threadManager.getSoftwareThreadExecutor().execute(() -> incomingEvent.parse(packet, client));
        } else {
            incomingEvent.parse(packet, client);
        }
    }

    @Override
    public void parseForGuest(IIncomingPacket packet, ChannelHandlerContext ctx) {
        try {
            var incomingEvent = guestEvents.get(packet.getHeader());
            if (incomingEvent == null) {
                if (incomingEvents.containsKey(packet.getHeader()))
                    clientManager.disconnectGuest(ctx);
                return;
            }

            sharedGuestExecutor.execute(() -> incomingEvent.parseForGuest(packet, ctx));
        }
        catch (Exception e) {
            clientManager.disconnectGuest(ctx);
            logger.error(e.getMessage(), e);
        }
    }
}
