package networking.util;

import networking.packets.IOutgoingPacket;

public interface ISerializable {
    void serialize(IOutgoingPacket packet);
}
