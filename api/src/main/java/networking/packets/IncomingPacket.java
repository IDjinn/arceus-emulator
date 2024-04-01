package networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.NotNull;

public class IncomingPacket {
    private final int header;
    private final ByteBuf buffer;

    public IncomingPacket(int messageId,@NotNull ByteBuf buffer) {
        this.header = messageId;
        this.buffer = ((buffer.readableBytes() == 0) ? Unpooled.EMPTY_BUFFER : buffer);
    }

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    public int getHeader() {
        return this.header;
    }


    public int readShort() {
        return this.buffer.readShort();
    }

    public Integer readInt() {
        return this.buffer.readInt();
    }

    public boolean readBoolean() {
        return this.buffer.readByte() == 1;
    }

    public String readString() {
        int length = this.readShort();
        byte[] data = new byte[length];
        this.buffer.readBytes(data);
        return new String(data);
    }

    public int getReadableBytes() {
        return this.buffer.readableBytes();
    }

    public boolean release() {
        return this.buffer.release();
    }
}