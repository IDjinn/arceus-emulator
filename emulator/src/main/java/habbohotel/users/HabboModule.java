package habbohotel.users;

import com.google.inject.AbstractModule;
import habbohotel.users.factories.HabboFactory;
import habbohotel.users.providers.ILoginProvider;
import habbohotel.users.providers.HabboLoginProvider;

public class HabboModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboFactory.class).to(HabboFactory.class);
        bind(IHabboManager.class).to(HabboManager.class);

        bind(ILoginProvider.class).to(HabboLoginProvider.class);
    }
}
