package packets.outgoing.navigator;

import habbo.habbos.data.navigator.IHabboNavigatorWindowSettings;
import packets.outgoing.OutgoingHeaders;
import packets.outgoing.OutgoingPacket;

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
