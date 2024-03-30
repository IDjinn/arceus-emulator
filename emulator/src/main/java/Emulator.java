import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import configuration.ConfigurationManager;
import configuration.IConfigurationManager;
import core.IEmulator;
import database.Database;
import database.DatabasePool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.database.IDatabase;
import storage.database.IDatabasePool;


@Singleton
public class Emulator extends AbstractModule implements IEmulator {
     private final Logger logger = LogManager.getLogger();
    @Inject private IConfigurationManager configurationManager;
    @Inject private IDatabase database;

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
    }

    @Override
    public IDatabase getDatabase() {
        return database;
    }

    @Override
    public void start() {
        logger.info("Orion has been started!");
        try {
            configurationManager.configureFromFile("config.properties");
            database.initialize();
            var database = getDatabase().runQuery(getDatabase().getDataSource().getConnection(), "SELECT VERSION()");
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
