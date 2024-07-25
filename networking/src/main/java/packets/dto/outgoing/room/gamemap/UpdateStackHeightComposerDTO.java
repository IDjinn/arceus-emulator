package packets.dto.outgoing.room.gamemap;

import habbo.rooms.components.gamemap.IRoomTile;
import networking.packets.IPacketDTO;

import java.util.List;

public record UpdateStackHeightComposerDTO(List<IRoomTile> tiles) implements IPacketDTO {
    public static UpdateStackHeightComposerDTO of(IRoomTile tile) {
        return new UpdateStackHeightComposerDTO(List.of(tile));
    }
}
