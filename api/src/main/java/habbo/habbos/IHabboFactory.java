package habbo.habbos;

import networking.client.INitroClient;
import storage.results.IConnectionResult;

public interface IHabboFactory {
    public IHabbo create(INitroClient client, int id, String name);

    public IHabbo create(INitroClient client, IConnectionResult result);
}
