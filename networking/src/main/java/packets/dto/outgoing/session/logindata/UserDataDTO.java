package packets.dto.outgoing.session.logindata;

import habbo.habbos.IHabbo;
import networking.packets.PacketDTO;

public record UserDataDTO(IHabbo habbo) implements PacketDTO {
    public PacketDTO of(final IHabbo habbo) {
        return new UserDataDTO(habbo);
    }
}
