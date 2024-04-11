package habbo.rooms.components.objects.items;

import utils.pathfinder.Position;

public interface IPositionable {
    Position getPosition();

    void setPosition(Position position);
}
