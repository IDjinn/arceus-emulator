package repositories;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import results.ConnectionResult;
import results.ConnectionResultConsumer;
import storage.IConnection;
import storage.repositories.IConnectionRepository;
import storage.results.IConnectionResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionRepository implements IConnectionRepository {
    @Inject
    private IConnection connection;

    protected Logger logger = LogManager.getLogger();

    public void select(String query, ConnectionResultConsumer resultConsumer, Object... parameters) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet selectResult = null;

        try {
            connection = this.connection.get();
            preparedStatement = connection.prepareStatement(query);

            this.setPreparedStatementParameters(preparedStatement, parameters);

            selectResult = preparedStatement.executeQuery();

            final IConnectionResult connectionResult = new ConnectionResult(selectResult);

            while (selectResult.next()) {
                resultConsumer.accept(connectionResult);
            }
        } catch (Exception e) {
            this.logger.error(STR."Error while executing select query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeResultSet(selectResult);
        }
    }

    private void setPreparedStatementParameters(PreparedStatement preparedStatement, Object... parameters) throws Exception {
        int parameterIndex = 1;

        for (Object obj : parameters) {
            switch (obj) {
                case Integer i -> preparedStatement.setInt(parameterIndex++, i);
                case String s -> preparedStatement.setString(parameterIndex++, s);
                case Long l -> preparedStatement.setLong(parameterIndex++, l);
                case Boolean b -> preparedStatement.setBoolean(parameterIndex++, b);
                case Double v -> preparedStatement.setDouble(parameterIndex++, v);
                case null, default -> {
                    if (obj != null) {
                        throw new Exception("Only [Integer, String, Long, Boolean, Double] types are supported!");
                    }

                    preparedStatement.setString(parameterIndex++, null);
                }
            }
        }
    }
}
