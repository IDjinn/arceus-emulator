package habbohotel.users;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import networking.client.INitroClientManager;


@Singleton
public class HabboManager implements IHabboManager {

    @Inject
    private INitroClientManager clientManager;
}
