package networking.packets.outgoing;

import networking.packets.IPacketDTO;
import networking.packets.IPacketWriter;

public interface IOutgoingDTOSerializer<DTO extends IPacketDTO> {
    void compose(final IPacketWriter writer, final DTO dto);
}