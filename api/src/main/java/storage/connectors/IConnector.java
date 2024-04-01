package storage.connectors;

import com.zaxxer.hikari.HikariDataSource;

public interface IConnector {
    public HikariDataSource getDataSource();
}
