import com.google.inject.Inject;
import com.google.inject.Singleton;
import storage.IConnection;
import storage.IConnectionContext;

@Singleton
public class Connection implements IConnection {
    @Inject
    private IConnectionContext connectionContext;

    @Override
    public java.sql.Connection get() throws Exception {
        return this.connectionContext.getProvider().getConnection();
    }

    public IConnectionContext getConnectionContext() {
        return this.connectionContext;
    }
}
