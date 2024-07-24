package packets.dto.outgoing.navigator;

import habbo.navigator.data.INavigatorEventCategory;
import networking.packets.IPacketDTO;

import java.util.List;

public record NewNavigatorEventCategoriesComposerDTO(List<INavigatorEventCategory> categories) implements IPacketDTO {
}
