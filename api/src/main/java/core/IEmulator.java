package core;

import networking.INetworkingManager;
import storage.database.IDatabase;

public interface IEmulator  {
    
    public IDatabase getDatabase();
    public INetworkingManager getNetworkingManager();
    
    public void start();
    public void shutdown();
}
