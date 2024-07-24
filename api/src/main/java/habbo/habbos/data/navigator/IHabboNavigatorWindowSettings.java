package habbo.habbos.data.navigator;

import networking.packets.IPacketDTO;

public interface IHabboNavigatorWindowSettings extends IPacketDTO {
    int getWindowX();

    int getWindowY();

    int getWindowWidth();

    int getWindowHeight();

    boolean isLeftPanelCollapsed();

    int getResultsMode();
}
