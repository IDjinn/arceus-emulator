package utils.interfaces;

import networking.packets.IOutgoingPacket;

public interface IWriteable {
    void write(IOutgoingPacket<U> packet);
}
