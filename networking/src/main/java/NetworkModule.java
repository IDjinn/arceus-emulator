import client.ClientFactory;
import client.ClientManager;
import com.google.inject.AbstractModule;
import decoders.IncomingPacketLogger;
import decoders.PacketParser;
import networking.INetworkingManager;
import networking.client.IClientFactory;
import networking.client.IClientManager;
import networking.packets.IPacketWriterFactory;
import networking.packets.incoming.IIncomingPacketProvider;
import networking.packets.IPacketManager;
import networking.packets.IPacketWriter;
import packets.IncomingPacketProvider;
import packets.PacketManager;
import packets.PacketWriter;
import packets.PacketWriterFactory;

public class NetworkModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IClientFactory.class).to(ClientFactory.class);
        this.bind(INetworkingManager.class).to(NetworkingManager.class);
        this.bind(IPacketManager.class).to(PacketManager.class);
        this.bind(IClientManager.class).to(ClientManager.class);
        this.bind(IncomingPacketLogger.class);
        this.bind(PacketParser.class);
        this.bind(IPacketWriter.class).to(PacketWriter.class);
        this.bind(IIncomingPacketProvider.class).to(IncomingPacketProvider.class);
        this.bind(IPacketWriterFactory.class).to(PacketWriterFactory.class);
    }
}
