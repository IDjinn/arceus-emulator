package networking.packets;

public interface OutgoingPacket<DTO extends IPacketDTO> {
    void compose(final IPacketWriter writer, final DTO dto);

    int getHeaderId();
}