package providers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import connectors.HikariConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.providers.IConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Singleton
public class ConnectionProvider implements IConnectionProvider {
    private final Logger logger = LogManager.getLogger();

    @Inject
    private HikariConnector hikariConnector;

    @Override
    public Connection getConnection() throws Exception {
        return this.hikariConnector.getDataSource().getConnection();
    }

    @Override
    public void closeConnection() {
        this.hikariConnector.getDataSource().close();
    }

    @Override
    public void closeStatement(PreparedStatement statement) {
        try {
            if (statement == null) return;

            statement.close();
        } catch (Exception e) {
            logger.error("Failed to close statement: {}", e.getMessage());
        }
    }
}
