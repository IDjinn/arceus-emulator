package storage.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public interface IDatabase {
    public void initialize();
    public void dispose();
    public HikariDataSource getDataSource();

    public IDatabasePool getDatabasePool();

    public PreparedStatement runQuery(Connection connection, String query) throws SQLException;
    public PreparedStatement preparedStatementWithParams(Connection connection, String query, HashMap<String, Object> queryParams) throws SQLException;
}
