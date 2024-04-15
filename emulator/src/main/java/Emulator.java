import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.IEmulator;
import core.IHotel;
import core.concurrency.IProcessHandler;
import core.concurrency.IThreadManager;
import core.concurrency.ProcessHandler;
import core.concurrency.ThreadManager;
import core.configuration.ConfigurationManager;
import core.configuration.ConfigurationModule;
import core.configuration.IConfigurationManager;
import core.configuration.IEmulatorSettings;
import core.events.EventHandler;
import core.events.IEventHandler;
import core.locking.ConcurrentLock;
import core.locking.IConcurrentLock;
import core.plugins.IPluginManager;
import core.plugins.PluginManager;
import core.security.IScriptManager;
import core.security.ScriptManager;
import habbo.catalog.CatalogModule;
import habbo.furniture.FurnitureModule;
import habbo.habbos.HabboModule;
import habbo.navigator.NavigatorModule;
import habbo.permissions.PermissionModule;
import habbo.rooms.RoomModule;
import habbo.rooms.components.objects.RoomObjectModule;
import networking.INetworkingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import packets.AutoBindIncomingEventsModule;
import repositories.RepositoryModule;


@Singleton
public class Emulator extends AbstractModule implements IEmulator {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private IEmulatorSettings emulatorSettings;

    @Inject
    private IHotel hotel;

    @Inject
    private INetworkingManager networkingManager;

    @Inject
    private IPluginManager pluginManager;

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
        this.bind(IEmulator.class).to(Emulator.class);
        this.bind(IConfigurationManager.class).to(ConfigurationManager.class);
        this.bind(IHotel.class).to(Hotel.class);
        this.bind(IConcurrentLock.class).to(ConcurrentLock.class);
        this.bind(IThreadManager.class).to(ThreadManager.class);
        this.bind(IScriptManager.class).to(ScriptManager.class);
        this.bind(IProcessHandler.class).to(ProcessHandler.class);
        this.bind(IPluginManager.class).to(PluginManager.class);
        this.bind(IEventHandler.class).to(EventHandler.class);
    }

    @Override
    public void start() {
        try {
            this.emulatorSettings.init();
            this.hotel.init();
            this.networkingManager.init();
            this.pluginManager.init();
            this.logger.info("Orion has been started!");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
    }

    @Override
    public void shutdown() {
        this.emulatorSettings.destroy();
        this.pluginManager.destroy();
        this.hotel.destroy();
        this.networkingManager.destroy();
        this.logger.info("Orion has been shutdown!");
    }
}