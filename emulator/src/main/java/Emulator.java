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
import habbohotel.Hotel;
import habbohotel.navigator.NavigatorModule;
import habbohotel.rooms.IRoomManager;
import habbohotel.rooms.RoomModule;
import habbohotel.users.HabboModule;
import networking.INetworkingManager;
import networking.packets.IPacketManager;
import networking.util.AutoBindIncomingEventsModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.database.IDatabase;
import storage.database.IDatabasePool;


@Singleton
public class Emulator extends AbstractModule implements IEmulator {
     private final Logger logger = LogManager.getLogger();
    @Inject private IConfigurationManager configurationManager;
    @Inject private IDatabase database;
    @Inject private IHotel hotel;
    @Inject private INetworkingManager networkingManager;
    @Inject
    private IRoomManager roomManager;

    @Inject
    private IPacketManager packetManager;
    
    
    public static void main(String[] args){
        var injector = Guice.createInjector(
                new Emulator(),
                new AutoBindIncomingEventsModule(),
                new NetworkModule(),
                new RoomModule(),
                new NavigatorModule(),
                new HabboModule()
        );
        var emulator = injector.getInstance(IEmulator.class);
        emulator.start();
    }


    @Override
    protected void configure() {
        bind(IDatabase.class).to(Database.class);
        bind(IDatabasePool.class).to(DatabasePool.class);
        bind(IEmulator.class).to(Emulator.class);
        bind(IConfigurationManager.class).to(ConfigurationManager.class);
        bind(IHotel.class).to(Hotel.class);
    }


    @Override
    public void start() {
        logger.info("Orion has been started!");
        try {
            database.initialize();
            var database = this.database.runQuery(this.database.getDataSource().getConnection(), "SELECT VERSION()");
            networkingManager.init();
            roomManager.init();
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
