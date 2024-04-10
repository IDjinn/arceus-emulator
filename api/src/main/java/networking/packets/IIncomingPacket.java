package networking.packets;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.Nullable;
import org.jsoup.safety.Cleaner;

public interface IIncomingPacket {
    int DEFAULT_MAX_STRING_LENGTH = 350;

    ByteBuf getBuffer();

    int getHeader();

    int readShort();

    Integer readInt();

    boolean readBoolean();

    String readString(@Nullable Cleaner cleaner, short maxLength);

    String readString();

    int getReadableBytes();

    boolean release();

    String readString(short maxLength);
}
