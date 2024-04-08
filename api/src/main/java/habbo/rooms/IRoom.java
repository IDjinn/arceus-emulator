package habbo.rooms;

import habbo.habbos.IHabbo;
import habbo.rooms.components.entities.IRoomEntityManager;
import habbo.rooms.components.gamemap.IRoomGameMap;
import habbo.rooms.components.objects.IRoomObjectManager;
import habbo.rooms.components.pathfinder.IPathfinder;
import habbo.rooms.components.rights.IRoomRightsManager;
import networking.packets.OutgoingPacket;
import networking.util.ISerializable;
import utils.IDisposable;

public interface IRoom extends Comparable<IRoom>, IDisposable, ISerializable {
    public int getId();

    public String getName();

    public String getPassword();

    public void setPassword(String password);

    public int getMaxUsers();

    public void setMaxUsers(int maxUsers);

    public int getMinUsers();

    public void setMinUsers(int minUsers);

    public boolean isPublic();

    public void setPublic(boolean isPublic);

    public RoomAccess getRoomAccess();

    public void setRoomAccess(RoomAccess roomAccess);


    public void init();

    public void destroy();

    public void onLoaded();

    void prepareForHabbo(IHabbo habbo, String password);

    void join(IHabbo habbo);

    void broadcastMessage(OutgoingPacket packet);

    void broadcastMessages(OutgoingPacket... packets);

    public IRoomEntityManager getEntityManager();

    public IRoomGameMap getGameMap();

    public IPathfinder getPathfinder();

    IRoomObjectManager getObjectManager();

    IRoomRightsManager getRightsManager();
}
