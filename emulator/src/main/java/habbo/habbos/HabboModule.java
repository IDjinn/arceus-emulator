package habbo.habbos;

import com.google.inject.AbstractModule;
import habbo.habbos.factories.HabboDataFactory;
import habbo.habbos.factories.HabboFactory;
import habbo.habbos.factories.IHabboDataFactory;
import habbo.habbos.factories.IHabboFactory;
import habbo.habbos.providers.ILoginProvider;
import habbo.habbos.providers.HabboLoginProvider;

public class HabboModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboDataFactory.class).to(HabboDataFactory.class);
        bind(IHabboFactory.class).to(HabboFactory.class);

        bind(IHabboManager.class).to(HabboManager.class);
        bind(ILoginProvider.class).to(HabboLoginProvider.class);
    }
}
