package habbohotel.users;

import networking.client.INitroClient;

public interface IHabboFactory {
    public IHabbo createHabbo(INitroClient client, int id, String name);
}
