package habbo.rooms;

import com.google.inject.AbstractModule;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.entities.RoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.gamemap.RoomRoomGameMap;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.pathfinder.Pathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import habbo.rooms.components.rights.RoomRightsManager;

public class RoomModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRoomFactory.class).to(RoomFactory.class);
        bind(IRoomManager.class).to(RoomManager.class);
        bind(IPathfinder.class).to(Pathfinder.class);
        bind(IRoomGameMap.class).to(RoomRoomGameMap.class);
        bind(IRoomEntityManager.class).to(RoomEntityManager.class);
        bind(IRoomRightsManager.class).to(RoomRightsManager.class);
    }
}
