package networking.packets;

public interface IPacketSerializer<T> {
    void serialize(IPacketWriter writer, T dto);
}
