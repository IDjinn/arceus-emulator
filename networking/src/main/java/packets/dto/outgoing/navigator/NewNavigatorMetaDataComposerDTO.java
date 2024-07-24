package packets.dto.outgoing.navigator;

import habbo.habbos.data.navigator.IHabboNavigatorSearch;
import networking.packets.IPacketDTO;

import java.util.List;

public record NewNavigatorMetaDataComposerDTO(List<IHabboNavigatorSearch> savedSearches) implements IPacketDTO {
    public static final String[] tabs = {
            "official_view",
            "hotel_view",
            "roomads_view",
            "myworld_view"
    };
}
