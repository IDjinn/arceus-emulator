package packets.outgoing.rooms.objects.floor.slide;

import utils.pathfinder.Position;

public record SlideObjectEntry(int virutalId, Position oldPosition, Position newPosition) {
}
