import client.ClientFactory;
import client.ClientManager;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import decoders.IncomingPacketLogger;
import decoders.PacketParser;
import networking.INetworkingManager;
import networking.client.IClientFactory;
import networking.client.IClientManager;
import networking.packets.IIncomingPacketProvider;
import networking.packets.IPacketManager;
import packets.IncomingEventAsListProvider;
import packets.PacketManager;
import packets.incoming.IncomingEvent;
import packets.incoming.IncomingPacketProvider;

import java.util.List;

public class NetworkModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<List<Class<? extends IncomingEvent>>>() {
        }).toProvider(IncomingEventAsListProvider.class);
        bind(IClientFactory.class).to(ClientFactory.class);
        bind(INetworkingManager.class).to(NetworkingManager.class);
        bind(IPacketManager.class).to(PacketManager.class);
        bind(IClientManager.class).to(ClientManager.class);
        bind(IncomingPacketLogger.class);
        bind(PacketParser.class);
        bind(IIncomingPacketProvider.class).to(IncomingPacketProvider.class);
    }
}
