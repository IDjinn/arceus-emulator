package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.entities.IPlayerEntity;
import habbo.rooms.entities.IRoomEntity;
import utils.cycle.ICycle;

import java.util.List;

public interface IRoomEntityManager extends IRoomComponent, ICycle {
    IPlayerEntity createHabboEntity(IHabbo habbo);

    List<IRoomEntity> getEntities();

    List<IPlayerEntity> getPlayers();

    void kick(habbo.rooms.entities.IRoomEntity roomEntity);
}
