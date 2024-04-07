package habbo.rooms;

import storage.results.IConnectionResult;

public interface IRoomFactory {
    public IRoom createRoom(IConnectionResult data);
}
