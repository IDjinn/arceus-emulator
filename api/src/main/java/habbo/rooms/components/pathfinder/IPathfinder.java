package habbo.rooms.components.pathfinder;

import habbo.rooms.IRoomComponent;
import habbo.rooms.components.gamemap.IGameMap;
import utils.Direction;
import utils.Position;

import java.util.HashMap;
import java.util.SequencedCollection;

public interface IPathfinder extends IRoomComponent {
    public boolean isEnabled3d();

    public void setEnabled3d(boolean enabled3d);


    public SequencedCollection<Position> tracePath(IGameMap gameMap, Position from, Position to);

    public HashMap<Direction, Position> getAdjacentDirections();

    public HashMap<Direction, Position> getDiagonalDirections();
}
