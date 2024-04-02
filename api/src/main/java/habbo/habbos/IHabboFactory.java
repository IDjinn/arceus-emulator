package habbo.habbos;

import networking.client.INitroClient;

public interface IHabboFactory {
    public IHabbo createHabbo(INitroClient client, int id, String name);
}
