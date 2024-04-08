package packets.outgoing.navigator;

import habbo.habbos.data.navigator.IHabboNavigatorWindowSettings;
import networking.packets.OutgoingPacket;
import packets.outgoing.OutgoingHeaders;

public class NewNavigatorSettingsComposer extends OutgoingPacket {
    public NewNavigatorSettingsComposer(final IHabboNavigatorWindowSettings settings) {
        super(OutgoingHeaders.NewNavigatorSettingsComposer);

        appendInt(settings.getWindowX());
        appendInt(settings.getWindowY());
        appendInt(settings.getWindowWidth());
        appendInt(settings.getWindowHeight());
        appendBoolean(settings.isLeftPanelCollapsed());
        appendInt(settings.getResultsMode());
    }
}
