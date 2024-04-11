package networking.util;

import networking.packets.OutgoingPacket;

public interface ISerializable {
    void serialize(OutgoingPacket packet);
}
