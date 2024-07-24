package networking.packets;

public interface OutgoingPacket<DTO extends PacketDTO> {
    void compose(final PacketWriter writer, final DTO dto);

    int getHeaderId();
}