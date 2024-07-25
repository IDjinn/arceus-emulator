package packets.dto.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.IPacketDTO;

public record UserDataComposerDTO(IHabbo habbo) implements IPacketDTO {
    public IPacketDTO of(final IHabbo habbo) {
        return new UserDataComposerDTO(habbo);
    }
}
