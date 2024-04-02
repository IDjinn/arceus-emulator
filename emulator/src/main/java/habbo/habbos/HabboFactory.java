package habbo.habbos;

import com.google.inject.Inject;
import com.google.inject.Injector;
import networking.client.INitroClient;

public class HabboFactory implements IHabboFactory {
    @Inject
    Injector injector;

    @Override
    public IHabbo createHabbo(INitroClient client, int id, String name) {
        var habbo = new Habbo(client, id, name);
        injector.injectMembers(habbo);
        return habbo;
    }
}
