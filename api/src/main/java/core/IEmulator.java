package core;

import storage.database.IDatabase;

public interface IEmulator  {
    
    public IDatabase getDatabase();
    
    
    public void start();
    public void shutdown();
}
