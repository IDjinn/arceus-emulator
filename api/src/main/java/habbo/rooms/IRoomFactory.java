package habbo.rooms;

import storage.results.IConnectionResult;

public interface IRoomFactory {
    IRoom createRoom(IConnectionResult data);
}
