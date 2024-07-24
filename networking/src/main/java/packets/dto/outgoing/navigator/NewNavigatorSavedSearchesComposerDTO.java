package packets.dto.outgoing.navigator;

import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import networking.packets.IPacketDTO;

import java.util.List;

public record NewNavigatorSavedSearchesComposerDTO(List<IHabboNavigatorSearch> searches) implements IPacketDTO {
}
