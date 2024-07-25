package habbo.rooms;

import core.concurrency.IProcessHandler;
import core.events.IEventHandler;
import habbo.commands.ICommandManager;
import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomChatComponent;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.objects.IRoomObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import habbo.rooms.components.variables.IRoomVariablesManager;
import habbo.rooms.data.IRoomData;
import habbo.rooms.data.IRoomModelData;
import networking.packets.outgoing.IOutgoingDTOSerializer;
import org.jetbrains.annotations.Nullable;
import utils.interfaces.IDisposable;

public interface IRoom extends Comparable<IRoom>, IDisposable {
    IRoomData getData();

    void init();

    void update();
    void destroy();

    void onLoaded();

    void prepareForHabbo(IHabbo habbo, String password);

    void join(IHabbo habbo);

    void broadcastMessage(IOutgoingDTOSerializer<?> packet);

    void broadcastMessages(IOutgoingDTOSerializer<?>... packets);

    IRoomEntityManager getEntityManager();

    IRoomGameMap getGameMap();

    IPathfinder getPathfinder();

    IRoomObjectManager getObjectManager();

    IRoomRightsManager getRightsManager();

    IRoomModelData getModel();

    IRoomVariablesManager getVariablesManager();
    
    boolean isFullyLoaded();

    void setFullyLoaded(boolean isFullyLoaded);

    IProcessHandler getProcessHandler();

    @Nullable <TRoomComponent extends IRoomComponent> TRoomComponent getCustomComponent(final Class<TRoomComponent> componentType);

    void registerCustomComponent(Class<? extends IRoomComponent> component, IRoomComponent instance);

    IEventHandler getEventHandler();

    ICommandManager getCommandManager();

    IRoomChatComponent getChatComponent();
}
