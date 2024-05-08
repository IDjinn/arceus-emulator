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
        this.bind(IHabboManager.class).to(HabboManager.class);
        this.bind(ILoginProvider.class).to(HabboLoginProvider.class);
        this.bind(IHabboFactory.class).to(HabboFactory.class);
        this.bind(IHabboInventoryItemFactory.class).to(HabboInventoryItemFactory.class);
    }
}
