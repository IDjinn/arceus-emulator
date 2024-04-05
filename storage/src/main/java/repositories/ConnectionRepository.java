package repositories;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import results.ConnectionResult;
import storage.IConnection;
import storage.data.IConnectionStatementConsumer;
import storage.repositories.IConnectionRepository;
import storage.results.IConnectionResult;
import storage.results.IConnectionResultConsumer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionRepository implements IConnectionRepository {
    @Inject
    private IConnection connection;

    protected Logger logger = LogManager.getLogger();

    public void select(String query, IConnectionResultConsumer resultConsumer, Object... parameters) {
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
            this.logger.error(STR."Error while executing SELECT query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeResultSet(selectResult);
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
        }
    }

    public void updateBatch(String query, IConnectionStatementConsumer statementConsumer, IConnectionResultConsumer resultConsumer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet updateResult = null;

        try {
            connection = this.connection.get();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statementConsumer.accept(preparedStatement);

            preparedStatement.executeBatch();

            updateResult = preparedStatement.getGeneratedKeys();

            final IConnectionResult connectionResult = new ConnectionResult(updateResult);

            while (updateResult.next()) {
                resultConsumer.accept(connectionResult);
            }
        } catch (Exception e) {
            this.logger.error(STR."Error while executing UPDATE query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeResultSet(updateResult);
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
        }
    }

    public void update(String query, IConnectionResultConsumer consumer, Object... parameters) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isUpdated;

        try {
            connection = this.connection.get();
            preparedStatement = connection.prepareStatement(query);

            this.setPreparedStatementParameters(preparedStatement, parameters);

            isUpdated = preparedStatement.executeUpdate() > 0;

            consumer.accept(isUpdated);
        } catch (Exception e) {
            this.logger.error(STR."Error while executing UPDATE query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
        }
    }

    public void insertBatch(String query, IConnectionStatementConsumer statementConsumer, IConnectionResultConsumer resultConsumer) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet insertResult = null;

        try {
            connection = this.connection.get();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statementConsumer.accept(preparedStatement);

            preparedStatement.executeBatch();

            insertResult = preparedStatement.getGeneratedKeys();

            final IConnectionResult connectionResult = new ConnectionResult(insertResult);

            while (insertResult.next()) {
                resultConsumer.accept(connectionResult);
            }
        } catch (Exception e) {
            this.logger.error(STR."Error while executing INSERT query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeResultSet(insertResult);
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
        }
    }

    public void insert(String query, IConnectionResultConsumer consumer, Object... parameters) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet insertResult = null;

        try {
            connection = this.connection.get();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            this.setPreparedStatementParameters(preparedStatement, parameters);

            preparedStatement.executeUpdate();

            insertResult = preparedStatement.getGeneratedKeys();

            final IConnectionResult connectionResult = new ConnectionResult(insertResult);

            while (insertResult.next()) {
                consumer.accept(connectionResult);
            }
        } catch (Exception e) {
            this.logger.error(STR."Error while executing INSERT query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeResultSet(insertResult);
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
        }
    }

    public void delete(String query, IConnectionResultConsumer consumer, Object... parameters) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted;

        try {
            connection = this.connection.get();
            preparedStatement = connection.prepareStatement(query);

            this.setPreparedStatementParameters(preparedStatement, parameters);

            isDeleted = preparedStatement.executeUpdate() > 0;

            consumer.accept(isDeleted);
        } catch (Exception e) {
            this.logger.error(STR."Error while executing DELETE query: \{query}", e);
        } finally {
            this.connection.getConnectionContext().getProvider().closeStatement(preparedStatement);
            this.connection.getConnectionContext().getProvider().closeConnection(connection);
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
