import com.google.inject.Inject;
import com.google.inject.Singleton;
import storage.IConnectionContext;
import storage.providers.IConnectionProvider;

@Singleton
public class ConnectionContext implements IConnectionContext {
    @Inject
    private IConnectionProvider connectionProvider;

    public IConnectionProvider getProvider() {
        return this.connectionProvider;
    }
}
