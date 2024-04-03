package habbo.habbos.factories;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbo.habbos.Habbo;
import habbo.habbos.IHabbo;
import networking.client.INitroClient;
import storage.results.IConnectionResult;

@Singleton
public class HabboFactory implements IHabboFactory {
    @Inject
    Injector injector;

    @Override
    public IHabbo create(INitroClient client, IConnectionResult result) {
        var habbo = new Habbo(client, result);
        injector.injectMembers(habbo);
        return habbo;
    }

}
