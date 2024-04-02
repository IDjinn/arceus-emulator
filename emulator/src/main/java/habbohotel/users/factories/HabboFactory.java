package habbohotel.users.factories;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import habbohotel.users.Habbo;
import habbohotel.users.IHabbo;
import habbohotel.users.IHabboFactory;
import networking.client.INitroClient;
import storage.results.IConnectionResult;

@Singleton
public class HabboFactory implements IHabboFactory {
    @Inject
    Injector injector;

    @Override
    public IHabbo create(INitroClient client, int id, String name) {
        var habbo = new Habbo(client, id, name);
        injector.injectMembers(habbo);
        return habbo;
    }

    @Override
    public IHabbo create(INitroClient client, IConnectionResult result) {
        var habbo = new Habbo(client, 1, "iNicollas");
        injector.injectMembers(habbo);
        return habbo;
    }

}
