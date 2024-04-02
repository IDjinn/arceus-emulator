import client.NitroClientFactory;
import client.NitroClientManager;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import decoders.IncomingPacketLogger;
import decoders.PacketParser;
import networking.INetworkingManager;
import networking.client.INitroClientFactory;
import networking.client.INitroClientManager;
import networking.packets.IPacketManager;
import packets.IncomingEventAsListProvider;
import packets.PacketManager;
import packets.incoming.IncomingEvent;

import java.util.List;

public class NetworkModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<List<Class<? extends IncomingEvent>>>() {
        }).toProvider(IncomingEventAsListProvider.class);
        bind(INitroClientFactory.class).to(NitroClientFactory.class);
        bind(INetworkingManager.class).to(NetworkingManager.class);
        bind(IPacketManager.class).to(PacketManager.class);
        bind(INitroClientManager.class).to(NitroClientManager.class);
        bind(IncomingPacketLogger.class);
        bind(PacketParser.class);
    }
}
