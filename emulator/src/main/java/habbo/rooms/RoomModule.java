package habbo.rooms;

import com.google.inject.AbstractModule;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.entities.RoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.gamemap.RoomGameMap;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.pathfinder.Pathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import habbo.rooms.components.rights.RoomRightsManager;
import habbo.rooms.components.variables.IRoomVariablesManager;
import habbo.rooms.components.variables.RoomVariablesManager;

public class RoomModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(IRoomFactory.class).to(RoomFactory.class);
        this.bind(IRoomManager.class).to(RoomManager.class);
        this.bind(IPathfinder.class).to(Pathfinder.class);
        this.bind(IRoomGameMap.class).to(RoomGameMap.class);
        this.bind(IRoomEntityManager.class).to(RoomEntityManager.class);
        this.bind(IRoomRightsManager.class).to(RoomRightsManager.class);
        this.bind(IRoomVariablesManager.class).to(RoomVariablesManager.class);
    }
}
