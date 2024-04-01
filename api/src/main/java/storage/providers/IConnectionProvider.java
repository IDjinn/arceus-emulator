package storage.providers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IConnectionProvider {
    public abstract Connection getConnection() throws Exception;

    public void closeConnection(Connection connection);

    public void closeStatement(PreparedStatement statement);

    public void closeResultSet(ResultSet resultSet);
}
