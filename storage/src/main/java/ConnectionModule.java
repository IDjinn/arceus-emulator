import com.google.inject.AbstractModule;
import connectors.HikariConnector;
import providers.ConnectionProvider;
import storage.IConnection;
import storage.IConnectionContext;
import storage.connectors.IConnector;
import storage.providers.IConnectionProvider;

public class ConnectionModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IConnector.class).to(HikariConnector.class);
        this.bind(IConnectionProvider.class).to(ConnectionProvider.class);
        this.bind(IConnectionContext.class).to(ConnectionContext.class);
        this.bind(IConnection.class).to(Connection.class);
    }
}
