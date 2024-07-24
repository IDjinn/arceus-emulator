package networking.util;

import networking.packets.OutgoingPacket;

public interface ISerializable {
    // TODO: THIS DOES NOT WORK WITH MULTI-REVISION
    void serialize(OutgoingPacket packet);
}
