package habbo.habbos.factories;

import habbo.habbos.IHabbo;
import networking.client.IClient;
import storage.results.IConnectionResult;

public interface IHabboFactory {
    IHabbo create(IClient client, IConnectionResult result);
}
