package utils.interfaces;

import networking.packets.OutgoingPacket;

public interface IWriteable {
    void write(OutgoingPacket packet);
}
