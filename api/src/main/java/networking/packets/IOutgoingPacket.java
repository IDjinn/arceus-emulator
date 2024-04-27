package networking.packets;

import io.netty.buffer.ByteBuf;

public interface IOutgoingPacket {

    IOutgoingPacket appendRawBytes(byte[] bytes);

    IOutgoingPacket appendRawBytes(byte[] bytes, String debugContext);

    IOutgoingPacket appendString(String string);

    IOutgoingPacket appendString(String string, String debugContext);

    IOutgoingPacket appendChar(int charValue);

    IOutgoingPacket appendChar(int obj, String debugContext);

    IOutgoingPacket appendInt(Integer integer);

    IOutgoingPacket appendInt(Integer integer, String debugContext);

    IOutgoingPacket appendBoolean(boolean bool);

    IOutgoingPacket appendBoolean(boolean bool, String debugContext);

    IOutgoingPacket appendDouble(double d);

    IOutgoingPacket appendDouble(double d, String debugContext);

    int getHeader();

    ByteBuf getBuffer();
}
