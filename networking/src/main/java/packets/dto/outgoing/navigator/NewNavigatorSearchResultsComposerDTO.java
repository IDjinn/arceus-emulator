package packets.dto.outgoing.navigator;

import habbo.navigator.data.INavigatorResultCategory;
import networking.packets.IPacketDTO;

import java.util.List;

public record NewNavigatorSearchResultsComposerDTO(String code, String query, List<INavigatorResultCategory> categories) implements IPacketDTO {
}
