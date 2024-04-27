package packets.outgoing;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import networking.packets.IOutgoingPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class OutgoingPacket implements IOutgoingPacket {
    private static final String StringEmpty = "";
    private static final Logger logger = LogManager.getLogger();
    private boolean initialized;

    private final int header;
    private AtomicInteger refs;
    private final ByteBufOutputStream stream;
    private final ByteBuf channelBuffer;

    public OutgoingPacket(int header) {
        this.header = header;
        this.channelBuffer = Unpooled.buffer();
        this.stream = new ByteBufOutputStream(this.channelBuffer);

        try {
            this.stream.writeInt(0);
            this.stream.writeShort(header);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public IOutgoingPacket appendRawBytes(byte[] bytes) {
        return appendRawBytes(bytes, StringEmpty);
    }

    public IOutgoingPacket appendRawBytes(byte[] bytes, String debugContext) {
        try {
            this.stream.write(bytes);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public IOutgoingPacket appendString(String string) {
        return appendString(string, StringEmpty);
    }

    public IOutgoingPacket appendString(String string, String debugContext) {
        if (string == null) {
            this.appendString(StringEmpty);
            return this;
        }

        try {
            byte[] data = string.getBytes();
            this.stream.writeShort(data.length);
            this.stream.write(data);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public IOutgoingPacket appendChar(int charValue) {
        return appendChar(charValue, StringEmpty);
    }

    public IOutgoingPacket appendChar(int obj, String debugContext) {
        try {
            this.stream.writeChar(obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public IOutgoingPacket appendInt(Integer integer) {
        return appendInt(integer, StringEmpty);
    }

    public IOutgoingPacket appendInt(Integer integer, String debugContext) {
        try {
            this.stream.writeInt(integer);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }


    public IOutgoingPacket appendBoolean(boolean bool) {
        return appendBoolean(bool, StringEmpty);
    }

    public IOutgoingPacket appendBoolean(boolean bool, String debugContext) {
        try {
            this.stream.writeBoolean(bool);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public IOutgoingPacket appendDouble(double d) {
        return appendDouble(d, StringEmpty);
    }

    public IOutgoingPacket appendDouble(double d, String debugContext) {
        try {
            this.stream.writeDouble(d);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public int getHeader() {
        return this.header;
    }

    public ByteBuf getBuffer() {
        this.channelBuffer.setInt(0, this.channelBuffer.writerIndex() - 4);

        return this.channelBuffer.copy();
    }

}