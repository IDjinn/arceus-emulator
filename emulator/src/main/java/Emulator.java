import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import configuration.ConfigurationManager;
import configuration.IConfigurationManager;
import core.IEmulator;
import core.IHotel;
import habbohotel.Hotel;
import habbohotel.rooms.IRoomManager;
import habbohotel.rooms.RoomModule;
import habbohotel.users.IUserManager;
import habbohotel.users.UserManager;
import networking.INetworkingManager;
import networking.packets.IPacketManager;
import networking.util.AutoBindIncomingEventsModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.IConnection;


@Singleton
public class Emulator extends AbstractModule implements IEmulator {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IConfigurationManager configurationManager;

    @Inject
    private IConnection connection;

    @Inject
    private IHotel hotel;

    @Inject
    private INetworkingManager networkingManager;

    @Inject
    private IRoomManager roomManager;

    @Inject
    private IPacketManager packetManager;

    public static void main(String[] args) {
        var injector = Guice.createInjector(
                new Emulator(),
                new AutoBindIncomingEventsModule(),
                new NetworkModule(),
                new RoomModule(),
                new ConnectionModule()
        );

        var emulator = injector.getInstance(IEmulator.class);

        assert emulator != null;

        emulator.start();
    }


    @Override
    protected void configure() {
        bind(IEmulator.class).to(Emulator.class);
        bind(IConfigurationManager.class).to(ConfigurationManager.class);
        bind(IHotel.class).to(Hotel.class);
        bind(IUserManager.class).to(UserManager.class);
    }


    @Override
    public void start() {
        logger.info("Orion has been started!");

        try {
            networkingManager.init();
            roomManager.init();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void shutdown() {
        logger.info("Orion has been shutdown!");
    }
}
