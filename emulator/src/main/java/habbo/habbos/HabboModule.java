package habbo.habbos;

import com.google.inject.AbstractModule;
import habbo.habbos.factories.HabboFactory;
import habbo.habbos.factories.HabboInventoryItemFactory;
import habbo.habbos.factories.IHabboFactory;
import habbo.habbos.factories.IHabboInventoryItemFactory;
import habbo.habbos.providers.HabboLoginProvider;
import habbo.habbos.providers.ILoginProvider;

public class HabboModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IHabboManager.class).to(HabboManager.class);
        bind(ILoginProvider.class).to(HabboLoginProvider.class);
        bind(IHabboFactory.class).to(HabboFactory.class);
        bind(IHabboInventoryItemFactory.class).to(HabboInventoryItemFactory.class);
    }
}
