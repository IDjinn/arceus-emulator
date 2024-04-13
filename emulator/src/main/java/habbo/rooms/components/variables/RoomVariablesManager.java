package habbo.rooms.components.variables;

import habbo.rooms.IRoom;
import habbo.variables.VariableManager;

public class RoomVariablesManager extends VariableManager implements IRoomVariablesManager {
    private IRoom room;

    @Override
    public IRoom getRoom() {
        return this.room;
    }

    @Override
    public void init(final IRoom room) {
        this.room = room;
    }

    @Override
    public void update() {
    }

    @Override
    public void onRoomLoaded() {
    }

    @Override
    public void destroy() {

    }
}
