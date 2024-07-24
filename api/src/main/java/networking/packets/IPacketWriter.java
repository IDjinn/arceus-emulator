package networking.packets;

import habbo.internationalization.LocalizedString;
import io.netty.buffer.ByteBuf;

public interface IPacketWriter {
    IPacketWriter appendRawBytes(byte[] bytes);

    IPacketWriter appendRawBytes(byte[] bytes, String debugContext);

    IPacketWriter appendString(LocalizedString localizedString);

    IPacketWriter appendString(String string);

    IPacketWriter appendString(String string, String debugContext);

    IPacketWriter appendChar(int charValue);

    IPacketWriter appendChar(int obj, String debugContext);

    IPacketWriter appendInt(Integer integer);

    IPacketWriter appendInt(Integer integer, String debugContext);

    IPacketWriter appendBoolean(boolean bool);

    IPacketWriter appendBoolean(boolean bool, String debugContext);

    IPacketWriter appendDouble(double d);

    IPacketWriter appendDouble(double d, String debugContext);

    ByteBuf getBuffer();

    IPacketWriter appendShort(int value);

    IPacketWriter appendShort(int value, String debugContext);
}
