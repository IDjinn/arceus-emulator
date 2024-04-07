package habbo.rooms;

import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntitiesComponent;
import habbo.rooms.components.gamemap.IGameMap;
import habbo.rooms.components.objects.IObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.data.IRoomData;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import utils.IDisposable;

public interface IRoom extends Comparable<IRoom>, IDisposable, ISerializable {
    IRoomData getData();

    void init();

    void destroy();

    void onLoaded();

    void prepareForHabbo(IHabbo habbo, String password);

    void join(IHabbo habbo);

    void broadcastMessage(OutgoingPacket packet);

    void broadcastMessages(OutgoingPacket... packets);

    IRoomEntitiesComponent getEntitiesComponent();

    IGameMap getGameMap();

    IPathfinder getPathfinder();

    IObjectManager getObjectManager();

    boolean isFullyLoaded();

    void setFullyLoaded(boolean isFullyLoaded);
}
