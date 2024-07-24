package habbo.rooms.components.objects;

import com.google.inject.AbstractModule;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import habbo.rooms.components.objects.items.RoomItemFactory;

public class RoomObjectModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IRoomObjectManager.class).to(RoomObjectManager.class);
        this.bind(IRoomItemFactory.class).to(RoomItemFactory.class);
    }
}
