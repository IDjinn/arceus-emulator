package storage.providers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IConnectionProvider {
    Connection getConnection() throws Exception;

    void closeConnection(Connection connection);

    void closeStatement(PreparedStatement statement);

    void closeResultSet(ResultSet resultSet);
}
