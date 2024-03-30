package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import configuration.IConfigurationManager;
import storage.database.IDatabasePool;

public class DatabasePool implements IDatabasePool {
    private HikariDataSource database;
    @Override
    public boolean getStoragePooling(IConfigurationManager config) {
        try {
            HikariConfig databaseConfiguration = new HikariConfig();
            databaseConfiguration.setMaximumPoolSize(config.getInt("db.pool.maxsize", 50));
            databaseConfiguration.setMinimumIdle(config.getInt("db.pool.minsize", 10));
            databaseConfiguration.setJdbcUrl(STR."jdbc:mysql://\{config.getString("db.hostname", "localhost")}:\{config.getString("db.port", "3306")}/\{config.getString("db.database", "habbo")}\{config.getString("db.params")}");
            databaseConfiguration.addDataSourceProperty("serverName", config.getString("db.hostname", "localhost"));
            databaseConfiguration.addDataSourceProperty("port", config.getString("db.port", "3306"));
            databaseConfiguration.addDataSourceProperty("databaseName", config.getString("db.database", "habbo"));
            databaseConfiguration.addDataSourceProperty("user", config.getString("db.username"));
            databaseConfiguration.addDataSourceProperty("password", config.getString("db.password"));
            databaseConfiguration.addDataSourceProperty("dataSource.logger", "com.mysql.jdbc.log.StandardLogger");
            databaseConfiguration.addDataSourceProperty("dataSource.logSlowQueries", "true");
            databaseConfiguration.addDataSourceProperty("dataSource.dumpQueriesOnException", "true");
            databaseConfiguration.addDataSourceProperty("prepStmtCacheSize", "500");
            databaseConfiguration.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            databaseConfiguration.addDataSourceProperty("cachePrepStmts", "true");
            databaseConfiguration.addDataSourceProperty("useServerPrepStmts", "true");
            databaseConfiguration.addDataSourceProperty("rewriteBatchedStatements", "true");
            databaseConfiguration.addDataSourceProperty("useUnicode", "true");
            databaseConfiguration.setAutoCommit(true);
            databaseConfiguration.setConnectionTimeout(300000L);
            databaseConfiguration.setValidationTimeout(5000L);
            databaseConfiguration.setLeakDetectionThreshold(20000L);
            databaseConfiguration.setMaxLifetime(1800000L);
            databaseConfiguration.setIdleTimeout(600000L);
            this.database = new HikariDataSource(databaseConfiguration);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public HikariDataSource getDatabase() {
        return this.database;
    }
}
