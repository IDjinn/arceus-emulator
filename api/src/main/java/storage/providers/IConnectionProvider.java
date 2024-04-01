package storage.providers;

import java.sql.Connection;
import java.sql.PreparedStatement;

public interface IConnectionProvider {
    public abstract Connection getConnection() throws Exception;

    public abstract void closeConnection();
    public abstract void closeStatement(PreparedStatement statement);
}
