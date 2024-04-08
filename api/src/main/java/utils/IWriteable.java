package utils;

import networking.packets.OutgoingPacket;

public interface IWriteable {
    void write(OutgoingPacket packet);
}
