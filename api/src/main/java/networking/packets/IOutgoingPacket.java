package networking.packets;

public interface IOutgoingPacket<DTO extends IPacketDTO> {
    void compose(final IPacketWriter writer, final DTO dto);

    int getHeaderId();
}