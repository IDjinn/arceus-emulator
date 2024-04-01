package providers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import connectors.HikariConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.providers.IConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            this.logger.error("Error while closing connection", e);
        }
    }

    @Override
    public void closeStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (Exception e) {
            this.logger.error("Error while closing statement", e);
        }
    }

    @Override
    public void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            this.logger.error("Error while closing result set", e);
        }
    }
}
