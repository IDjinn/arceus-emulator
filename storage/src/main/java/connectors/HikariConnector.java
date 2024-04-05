package connectors;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import core.configuration.IConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.connectors.IConnector;

@Singleton
public class HikariConnector implements IConnector {
    private final Logger logger = LogManager.getLogger();

    private final IConfigurationManager config;

    private HikariDataSource dataSource;

    @Inject
    public HikariConnector(IConfigurationManager config) {
        this.config = config;

        try {
            this.initializeHikari();
        } catch (Exception e) {
            logger.error(STR."Failed to initialize Hikari: \{e.getMessage()}");
        }
    }

    private void initializeHikari() {
        HikariConfig databaseConfiguration = new HikariConfig();

        databaseConfiguration.setMaximumPoolSize(this.config.getInt("db.pool.maxsize", 50));
        databaseConfiguration.setMinimumIdle(this.config.getInt("db.pool.minsize", 10));
        databaseConfiguration.setJdbcUrl(STR."jdbc:mysql://\{this.config.getString("db.hostname", "localhost")}:\{this.config.getString("db.port", "3306")}/\{this.config.getString("db.database", "habbo")}\{this.config.getString("db.params")}");
        databaseConfiguration.addDataSourceProperty("serverName", this.config.getString("db.hostname", "localhost"));
        databaseConfiguration.addDataSourceProperty("port", this.config.getString("db.port", "3306"));
        databaseConfiguration.addDataSourceProperty("databaseName", this.config.getString("db.database", "habbo"));
        databaseConfiguration.addDataSourceProperty("user", this.config.getString("db.username"));
        databaseConfiguration.addDataSourceProperty("password", this.config.getString("db.password"));
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

        this.dataSource = new HikariDataSource(databaseConfiguration);

        logger.info("Database connection established.");
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }
}
