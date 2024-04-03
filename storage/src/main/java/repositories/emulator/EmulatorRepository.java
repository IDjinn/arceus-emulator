package repositories.emulator;

import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import queries.emulator.EmulatorQuery;
import repositories.ConnectionRepository;
import storage.repositories.emulator.IEmulatorRepository;
import storage.results.IConnectionResultConsumer;

@Singleton
public class EmulatorRepository extends ConnectionRepository implements IEmulatorRepository {
    protected Logger logger = LogManager.getLogger();

    public void loadAllSettings(IConnectionResultConsumer consumer) {
        this.select(EmulatorQuery.LOAD_ALL_SETTINGS.get(), consumer);
    }
}
