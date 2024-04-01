package networking.packets.outgoing.navigator;

import networking.packets.OutgoingPacket;
import networking.packets.outgoing.OutgoingHeaders;

public class NewNavigatorSettingsComposer extends OutgoingPacket {
    public NewNavigatorSettingsComposer() {
        super(OutgoingHeaders.NewNavigatorSettingsComposer);
        var windowSettings = new HabboNavigatorWindowSettings();
        appendInt(windowSettings.x);
        appendInt(windowSettings.y);
        appendInt(windowSettings.width);
        appendInt(windowSettings.height);
        appendBoolean(windowSettings.openSearches);
        appendInt(0);
    }

    public class HabboNavigatorWindowSettings {
        public int x = 100;
        public int y = 100;
        public int width = 425;
        public int height = 535;
        public boolean openSearches = false;
        public int unknown = 0;
    }
}
