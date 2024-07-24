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
import networking.packets.IPacketWriter;
import networking.packets.IncomingEvent;
import packets.IncomingEventAsListProvider;
import packets.IncomingPacketProvider;
import packets.PacketManager;
import packets.PacketWriter;

import java.util.List;

public class NetworkModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(new TypeLiteral<List<Class<? extends IncomingEvent>>>() {
        }).toProvider(IncomingEventAsListProvider.class);
        this.bind(IClientFactory.class).to(ClientFactory.class);
        this.bind(INetworkingManager.class).to(NetworkingManager.class);
        this.bind(IPacketManager.class).to(PacketManager.class);
        this.bind(IClientManager.class).to(ClientManager.class);
        this.bind(IncomingPacketLogger.class);
        this.bind(PacketParser.class);
        this.bind(IPacketWriter.class).to(PacketWriter.class);
        this.bind(IIncomingPacketProvider.class).to(IncomingPacketProvider.class);
    }
}
