package habbo.rooms;

import com.google.inject.AbstractModule;
import habbo.rooms.components.entities.IRoomEntitiesComponent;
import habbo.rooms.components.entities.RoomEntitiesComponent;
import habbo.rooms.components.gamemap.GameMap;
import habbo.rooms.components.gamemap.IGameMap;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.pathfinder.Pathfinder;

public class RoomModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IRoomFactory.class).to(RoomFactory.class);
        bind(IRoomManager.class).to(RoomManager.class);
        bind(IPathfinder.class).to(Pathfinder.class);
        bind(IGameMap.class).to(GameMap.class);
        bind(IRoomEntitiesComponent.class).to(RoomEntitiesComponent.class);
    }
}
