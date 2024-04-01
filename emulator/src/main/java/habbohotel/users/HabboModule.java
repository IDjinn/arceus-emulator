package habbohotel.users;

import com.google.inject.AbstractModule;

public class HabboModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboFactory.class).to(HabboFactory.class);
        bind(IHabboManager.class).to(HabboManager.class);
    }
}
