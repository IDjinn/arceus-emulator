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
        bind(IConnector.class).to(HikariConnector.class);
        bind(IConnectionProvider.class).to(ConnectionProvider.class);
        bind(IConnectionContext.class).to(ConnectionContext.class);
        bind(IConnection.class).to(Connection.class);
    }
}
