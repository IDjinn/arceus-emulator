package packets.dto.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.IPacketDTO;

public record UserDataDTO(IHabbo habbo) implements IPacketDTO {
    public IPacketDTO of(final IHabbo habbo) {
        return new UserDataDTO(habbo);
    }
}
