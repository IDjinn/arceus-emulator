package habbohotel.rooms;

import com.google.inject.AbstractModule;

public class RoomModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRoomFactory.class).to(RoomFactory.class);
        bind(IRoomManager.class).to(RoomManager.class);
    }
}
