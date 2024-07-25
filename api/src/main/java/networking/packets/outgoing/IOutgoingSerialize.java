package networking.packets.outgoing;

import networking.packets.IPacketWriter;

public interface IOutgoingSerialize<T> {
    void serialize(IPacketWriter writer, T payload);
}
