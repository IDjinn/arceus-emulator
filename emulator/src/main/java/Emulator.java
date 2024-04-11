import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.IEmulator;
import core.IHotel;
import core.IThreadManager;
import core.ThreadManager;
import core.configuration.ConfigurationManager;
import core.configuration.ConfigurationModule;
import core.configuration.IConfigurationManager;
import core.configuration.IEmulatorSettings;
import core.locking.ConcurrentLock;
import core.locking.IConcurrentLock;
import core.security.IScriptManager;
import core.security.ScriptManager;
import habbo.Hotel;
import habbo.catalog.CatalogModule;
import habbo.catalog.ICatalogManager;
import habbo.furniture.FurnitureModule;
import habbo.furniture.IFurnitureManager;
import habbo.habbos.HabboModule;
import habbo.navigator.INavigatorManager;
import habbo.navigator.NavigatorModule;
import habbo.permissions.PermissionModule;
import habbo.rooms.IRoomManager;
import habbo.rooms.RoomModule;
import habbo.rooms.components.objects.RoomObjectModule;
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
    @Inject
    IThreadManager threadManager;

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

    @Inject
    private ICatalogManager catalogManager;

    @Inject
    private INavigatorManager navigatorManager;

    @Inject
    private IScriptManager scriptManager;

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
                new CatalogModule(),
                new PermissionModule(),
                new RoomObjectModule()
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
        bind(IConcurrentLock.class).to(ConcurrentLock.class);
        bind(IThreadManager.class).to(ThreadManager.class);
        bind(IScriptManager.class).to(ScriptManager.class);
    }


    @Override
    public void start() {
        this.logger.info("Orion has been started!");

        try {
            this.emulatorSettings.init();
            this.networkingManager.init();

            this.furnitureManager.init();
            this.catalogManager.init();

            this.roomManager.init();
            this.navigatorManager.init();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

    @Override
    public void shutdown() {
        this.logger.info("Orion has been shutdown!");
    }
}
