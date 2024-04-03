import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import configuration.ConfigurationManager;
import configuration.ConfigurationModule;
import configuration.IConfigurationManager;
import configuration.IEmulatorSettings;
import core.IEmulator;
import core.IHotel;
import furniture.FurnitureModule;
import habbo.Hotel;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.HabboModule;
import habbo.navigator.NavigatorModule;
import habbo.rooms.IRoomManager;
import habbo.rooms.RoomModule;
import habbo.rooms.RoomPrepareJoinPipeline;
import habbo.rooms.components.objects.ObjectModule;
import networking.INetworkingManager;
import networking.packets.IPacketManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.AutoBindIncomingEventsModule;
import repositories.RepositoryModule;
import storage.IConnection;


@Singleton
public class Emulator extends AbstractModule implements IEmulator {
    private final Logger logger = LogManager.getLogger();

    /**
     * The configuration manager (based in config.properties local file).
     */
    @Inject
    private IConfigurationManager configurationManager;

    /**
     * The emulator settings (based in the emulator_settings table).
     */
    @Inject
    private IEmulatorSettings emulatorSettings;

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
    @Inject
    private IFurnitureManager furnitureManager;

    public static void main(String[] args) {
        var injector = Guice.createInjector(
                new Emulator(),
                new RepositoryModule(),
                new AutoBindIncomingEventsModule(),
                new NetworkModule(),
                new RoomModule(),
                new ConnectionModule(),
                new NavigatorModule(),
                new HabboModule(),
                new ConfigurationModule(),
                new FurnitureModule(),
                new ObjectModule()
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
        bind(RoomPrepareJoinPipeline.class);
    }


    @Override
    public void start() {
        logger.info("Orion has been started!");

        try {
            emulatorSettings.init();
            hotel.init();
            furnitureManager.init();
            roomManager.init();
            networkingManager.init();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void shutdown() {
        logger.info("Orion has been shutdown!");
    }
}
