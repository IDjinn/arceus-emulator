package habbo.rooms;

import core.concurrency.IProcessHandler;
import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.objects.IRoomObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import habbo.rooms.data.IRoomData;
import habbo.rooms.data.IRoomModelData;
import networking.packets.OutgoingPacket;
import org.jetbrains.annotations.Nullable;
import utils.interfaces.IDisposable;
import utils.interfaces.IWriteable;

public interface IRoom extends Comparable<IRoom>, IDisposable, IWriteable {
    IRoomData getData();

    void init();

    void update();
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

    IProcessHandler getProcessHandler();

    @Nullable <TRoomComponent extends IRoomComponent> TRoomComponent getCustomComponent(final Class<TRoomComponent> componentType);

    void registerCustomComponent(Class<? extends IRoomComponent> component, IRoomComponent instance);
}
