package packets;

import io.netty.buffer.ByteBuf;
import networking.packets.IPacketWriter;
import networking.packets.IPacketWriterFactory;

public class PacketWriterFactory implements IPacketWriterFactory {
    @Override
    public IPacketWriter create(ByteBuf buffer) {
        return new PacketWriter(buffer);
    }
}
