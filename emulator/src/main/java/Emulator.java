import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import configuration.ConfigurationManager;
import configuration.IConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Emulator extends AbstractModule {
     private final Logger logger = LogManager.getLogger();
    @Inject private IConfigurationManager configurationManager;

    public static void main(String[] args){
        var injector = Guice.createInjector(new Emulator());
        var emulator = injector.getInstance(Emulator.class);
        emulator.run();
    }

    public void run() {
        logger.error("Orion emulator -> init");
        logger.info("Orion emulator -> done!");
    }

    @Override
    protected void configure() {
        bind(IConfigurationManager.class).to(ConfigurationManager.class);
    }
}
