package storage.database;

import com.zaxxer.hikari.HikariDataSource;
import configuration.IConfigurationManager;


public interface IDatabasePool {

    public boolean getStoragePooling(IConfigurationManager config);

    public HikariDataSource getDatabase() ;
}