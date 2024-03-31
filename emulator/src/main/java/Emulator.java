import client.NitroClientManager;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import configuration.ConfigurationManager;
import configuration.IConfigurationManager;
import core.IEmulator;
import core.IHotel;
import database.Database;
import database.DatabasePool;
import decoders.IncomingPacketLogger;
import decoders.PacketParser;
import habbohotel.Hotel;
import networking.INetworkingManager;
import networking.client.INitroClientManager;
import networking.packets.IPacketManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.PacketManager;
import storage.database.IDatabase;
import storage.database.IDatabasePool;


@Singleton
public class Emulator extends AbstractModule implements IEmulator {
     private final Logger logger = LogManager.getLogger();
    @Inject private IConfigurationManager configurationManager;
    @Inject private IDatabase database;
    @Inject private IHotel hotel;
    @Inject private INetworkingManager networkingManager;

    public static void main(String[] args){
        var injector = Guice.createInjector(new Emulator());
        var emulator = injector.getInstance(IEmulator.class);
        emulator.start();
    }


    @Override
    protected void configure() {
        bind(IDatabase.class).to(Database.class);
        bind(IDatabasePool.class).to(DatabasePool.class);
        bind(IEmulator.class).to(Emulator.class);
        bind(IConfigurationManager.class).to(ConfigurationManager.class);
        bind(INetworkingManager.class).to(NetworkingManager.class);
        bind(IPacketManager.class).to(PacketManager.class);
        bind(INitroClientManager.class).to(NitroClientManager.class);
        bind(IncomingPacketLogger.class);
        bind(PacketParser.class);
        bind(IHotel.class).to(Hotel.class);
    }

    @Override
    public IDatabase getDatabase() {
        return database;
    }

    @Override
    public INetworkingManager getNetworkingManager() {
        return networkingManager;
    }

    @Override
    public void start() {
        logger.info("Orion has been started!");
        try {
            database.initialize();
            var database = getDatabase().runQuery(getDatabase().getDataSource().getConnection(), "SELECT VERSION()");
            networkingManager.init();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
        logger.info("Orion has been connected to the database!");
    }

    @Override
    public void shutdown() {
        logger.info("Orion has been shutdown!");
    }
}
