package habbo.habbos.factories;

import habbo.habbos.IHabbo;
import networking.client.INitroClient;
import storage.results.IConnectionResult;

public interface IHabboFactory {
    IHabbo create(INitroClient client, IConnectionResult result);
}
