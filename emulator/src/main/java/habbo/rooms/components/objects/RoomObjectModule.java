package habbo.rooms.components.objects;

import com.google.inject.AbstractModule;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import habbo.rooms.components.objects.items.RoomItemFactory;

public class RoomObjectModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRoomObjectManager.class).to(RoomObjectManager.class);
        bind(IRoomItemFactory.class).to(RoomItemFactory.class);
    }
}
