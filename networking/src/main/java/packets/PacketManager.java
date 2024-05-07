package packets;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import core.concurrency.IThreadManager;
import core.configuration.IConfigurationManager;
import io.netty.channel.ChannelHandlerContext;
import networking.client.IClient;
import networking.client.IClientManager;
import networking.packets.IIncomingPacket;
import networking.packets.IPacketManager;
import networking.packets.IncomingEvent;
import networking.util.NoAuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ReflectionHelpers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class PacketManager implements IPacketManager {
    private final Logger logger = LogManager.getLogger();
    private final Executor sharedGuestExecutor = Executors.newSingleThreadExecutor();
    private final IClientManager clientManager;
    private final IConfigurationManager configuration;
    private final IThreadManager threadManager;

    private final HashMap<Integer, IncomingEvent> incomingEvents = new HashMap<>();
    private final HashMap<Integer, IncomingEvent> guestEvents = new HashMap<>();

    @Inject
    public PacketManager(IClientManager clientManager, List<Class<? extends IncomingEvent>> incomings, Injector injector, IConfigurationManager configuration, IThreadManager threadManager) {
        this.clientManager = clientManager;
        this.configuration = configuration;
        this.threadManager = threadManager;

        for (Class<? extends IncomingEvent> incoming : incomings) {
            if (incoming.isAnnotationPresent(NoAuth.class)) registerGuestEvent(injector.getProvider(incoming).get());
            else this.internalRegisterIncoming(injector.getProvider(incoming).get());
        }
    }

    private void internalRegisterIncoming(IncomingEvent incomingEvent) {
        this.incomingEvents.put(incomingEvent.getHeaderId(), incomingEvent);
    }

    @Override
    public void registerIncoming(IncomingEvent incomingEvent) {
        this.logger.debug(STR."register incoming \{incomingEvent.getClass().getSimpleName()} with header id \{incomingEvent.getHeaderId()} from \{ReflectionHelpers.getCallerInfo()}");
        this.internalRegisterIncoming(incomingEvent);
    }


    private void registerGuestEvent(IncomingEvent event) {
        this.guestEvents.put(event.getHeaderId(), event);
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
        if (this.incomingEvents.containsKey(headerId))
            return this.incomingEvents.get(headerId).getClass().getName();

        if (this.guestEvents.containsKey(headerId))
            return this.guestEvents.get(headerId).getClass().getName();

        return "Unknown";
    }

    private final HashSet<Integer> notFoundPackets = new HashSet<>();
    @Override
    public void parse(IIncomingPacket packet, IClient client) {
        var incomingEvent = this.incomingEvents.get(packet.getHeader());
        if (incomingEvent == null) {
            if (this.notFoundPackets.add(packet.getHeader()))
                this.logger.warn("[-> incoming] {} was not found", packet.getHeader());
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
            var incomingEvent = this.guestEvents.get(packet.getHeader());
            if (incomingEvent == null) {
                if (this.incomingEvents.containsKey(packet.getHeader()))
                    this.clientManager.disconnectGuest(ctx);
                return;
            }

            this.sharedGuestExecutor.execute(() -> incomingEvent.parseForGuest(packet, ctx));
        }
        catch (Exception e) {
            this.clientManager.disconnectGuest(ctx);
            this.logger.error(e.getMessage(), e);
        }
    }
}
