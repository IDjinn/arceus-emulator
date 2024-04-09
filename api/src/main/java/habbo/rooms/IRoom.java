package habbo.rooms;

import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.objects.IRoomObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import habbo.rooms.data.IRoomData;
import habbo.rooms.data.models.IRoomModelData;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.NotNull;
import utils.IDisposable;
import utils.IWriteable;

import java.util.concurrent.TimeUnit;

public interface IRoom extends Comparable<IRoom>, IDisposable, IWriteable {
    IRoomData getData();

    void init();

    void destroy();

    void onLoaded();

    void prepareForHabbo(IHabbo habbo, String password);

    void join(IHabbo habbo);

    void broadcastMessage(OutgoingPacket packet);

    void broadcastMessages(OutgoingPacket... packets);

    IRoomEntityManager getEntityManager();

    IRoomGameMap getGameMap();

    IPathfinder getPathfinder();

    IRoomObjectManager getObjectManager();

    IRoomRightsManager getRightsManager();

    IRoomModelData getModel();
    
    boolean isFullyLoaded();

    void setFullyLoaded(boolean isFullyLoaded);

    void registerProcess(@NotNull String key, Runnable runnable, long interval, TimeUnit timeUnit);

    boolean unregisterProcess(@NotNull String key);
}
