package database;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import configuration.IConfigurationManager;
import core.IEmulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import storage.database.IDatabase;
import storage.database.IDatabasePool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Database implements IDatabase {
    @Inject
    private final IEmulator emulator;
    @Inject private final IConfigurationManager config;
    private final Logger logger = LogManager.getLogger();

    private HikariDataSource dataSource;
    @Inject private final IDatabasePool databasePool;

    
    @Override
    public void initialize() {
        assert this.databasePool != null;
        assert this.config != null;
        long millis = System.currentTimeMillis();

        boolean SQLException = false;

        try {
            if (!this.databasePool.getStoragePooling(this.config)) {
                logger.info("Failed to connect to the database. Please check config.ini and make sure the MySQL process is running. Shutting down...");
                SQLException = true;
                return;
            }
            this.dataSource = this.databasePool.getDatabase();
        } catch (Exception e) {
            SQLException = true;
            logger.error("Failed to connect to your database.", e);
        } finally {
            if (SQLException) {
                emulator.shutdown();
            }
        }

        logger.info("Database -> Connected! ({} ms)", System.currentTimeMillis() - millis);
    }

    @Inject 
    public Database(IConfigurationManager config, IEmulator emulator, IDatabasePool databasePool) {
        this.config = config;
        this.emulator = emulator;
        this.databasePool = databasePool;
    }

    public void dispose() {
        if (this.databasePool != null) {
            this.databasePool.getDatabase().close();
        }

        this.dataSource.close();
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }

    public IDatabasePool getDatabasePool() {
        return this.databasePool;
    }

    @Override
    public PreparedStatement runQuery(Connection connection, String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public PreparedStatement preparedStatementWithParams(Connection connection, String query, HashMap<String, Object> queryParams) throws SQLException {
        HashMap<Integer, Object> params = new HashMap<Integer, Object>();
        HashSet<String> quotedParams = new HashSet<>();

        for(String key : queryParams.keySet()) {
            quotedParams.add(Pattern.quote(key));
        }

        String regex = STR."(\{String.join("|", quotedParams)})";

        Matcher m = Pattern.compile(regex).matcher(query);

        int i = 1;

        while (m.find()) {
            try {
                params.put(i, queryParams.get(m.group(1)));
                i++;
            }
            catch (Exception ignored) { }
        }

        PreparedStatement statement = connection.prepareStatement(query.replaceAll(regex, "?"));

        for(Map.Entry<Integer, Object> set : params.entrySet()) {
            if(set.getValue().getClass() == String.class) {
                statement.setString(set.getKey(), (String)set.getValue());
            }
            else if(set.getValue().getClass() == Integer.class) {
                statement.setInt(set.getKey(), (Integer)set.getValue());
            }
            else if(set.getValue().getClass() == Double.class) {
                statement.setDouble(set.getKey(), (Double)set.getValue());
            }
            else if(set.getValue().getClass() == Float.class) {
                statement.setFloat(set.getKey(), (Float)set.getValue());
            }
            else if(set.getValue().getClass() == Long.class) {
                statement.setLong(set.getKey(), (Long)set.getValue());
            }
            else {
                statement.setObject(set.getKey(), set.getValue());
            }
        }

        return statement;
    }

}
