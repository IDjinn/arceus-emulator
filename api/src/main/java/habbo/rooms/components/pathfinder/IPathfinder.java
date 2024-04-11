package habbo.rooms.components.pathfinder;

import habbo.rooms.IRoomComponent;
import utils.pathfinder.Direction;
import utils.pathfinder.Position;

import java.util.HashMap;
import java.util.SequencedCollection;

public interface IPathfinder extends IRoomComponent {
    boolean isEnabled3d();

    void setEnabled3d(boolean enabled3d);


    SequencedCollection<Position> tracePath(final Position from, final Position to);

    HashMap<Direction, Position> getAdjacentDirections();

    HashMap<Direction, Position> getDiagonalDirections();
}
