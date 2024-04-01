package networking.util;

import networking.packets.OutgoingPacket;

public interface ISerializable {
    public void serialize(OutgoingPacket packet);
}
