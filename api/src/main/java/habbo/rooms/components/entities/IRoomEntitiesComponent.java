package habbo.rooms.components.entities;

import habbo.habbos.IHabbo;
import habbo.rooms.IRoomComponent;
import habbo.rooms.entities.IHabboEntity;
import habbo.rooms.entities.IRoomEntity;

import java.util.List;

public interface IRoomEntitiesComponent extends IRoomComponent {
    IHabboEntity createHabboEntity(IHabbo habbo);

    List<IRoomEntity> getEntities();

    List<IHabboEntity> getPlayers();
}
