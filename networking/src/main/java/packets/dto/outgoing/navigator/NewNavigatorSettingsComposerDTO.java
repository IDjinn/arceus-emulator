package packets.dto.outgoing.navigator;

import networking.packets.IPacketDTO;

public record NewNavigatorSettingsComposerDTO(int windowX, int windowY, int windowWidth, int windowHeight, boolean isLeftPanelCollapsed, int resultsMode) implements IPacketDTO {
}
