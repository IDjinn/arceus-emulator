package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.entities.IHabboEntity;
import habbo.rooms.entities.IRoomEntity;
import utils.cycle.ICycle;

import java.util.List;

public interface IRoomEntityManager extends IRoomComponent, ICycle {
    IHabboEntity createHabboEntity(IHabbo habbo);

    List<IRoomEntity> getEntities();

    List<IHabboEntity> getPlayers();

    int getVirtualIdForEntity(IRoomEntity entity);
}
