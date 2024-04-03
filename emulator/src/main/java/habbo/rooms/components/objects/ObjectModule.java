package habbo.rooms.components.objects;

import com.google.inject.AbstractModule;
import habbo.rooms.components.objects.items.IRoomItemFactory;
import habbo.rooms.components.objects.items.RoomItemFactory;

public class ObjectModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IObjectManager.class).to(ObjectManager.class);
        bind(IRoomItemFactory.class).to(RoomItemFactory.class);
    }
}
