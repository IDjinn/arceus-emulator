package packets.outgoing.navigator;

import habbo.habbos.data.navigator.IHabboNavigatorWindowSettings;
import networking.packets.outgoing.IOutgoingEvent;

public interface INewNavigatorSettingsComposer extends IOutgoingEvent, networking.packets.outgoing.IOutgoingDTOSerializer<IHabboNavigatorWindowSettings> {
}
