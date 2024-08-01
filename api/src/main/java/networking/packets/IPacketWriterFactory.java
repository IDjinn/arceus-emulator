package networking.packets;

import com.google.inject.Provider;
import io.netty.buffer.ByteBuf;

public interface IPacketWriterFactory {
    IPacketWriter create(final ByteBuf buffer);
}
