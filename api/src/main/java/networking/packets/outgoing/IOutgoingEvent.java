package networking.packets.outgoing;

import networking.packets.IPacketDTO;
import networking.packets.IPacketWriter;

public interface IOutgoingEvent <DTO extends IPacketDTO> {
    int getHeaderId();
    void encode(final IPacketWriter writer, final DTO dto);
}
