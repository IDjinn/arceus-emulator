package networking.packets;

import habbo.internationalization.LocalizedString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class OutgoingPacket {
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

    public OutgoingPacket appendRawBytes(byte[] bytes) {
        return this.appendRawBytes(bytes, StringEmpty);
    }

    public OutgoingPacket appendRawBytes(byte[] bytes, String debugContext) {
        try {
            this.stream.write(bytes);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public OutgoingPacket appendString(LocalizedString localizedString) {
        return this.appendString(localizedString.getKey(), StringEmpty);
    }
    public OutgoingPacket appendString(String string) {
        return this.appendString(string, StringEmpty);
    }

    public OutgoingPacket appendString(String string, String debugContext) {
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

    public OutgoingPacket appendChar(int charValue) {
        return this.appendChar(charValue, StringEmpty);
    }

    public OutgoingPacket appendChar(int obj, String debugContext) {
        try {
            this.stream.writeChar(obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public OutgoingPacket appendInt(Integer integer) {
        return this.appendInt(integer, StringEmpty);
    }

    public OutgoingPacket appendInt(Integer integer, String debugContext) {
        try {
            this.stream.writeInt(integer);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }


    public OutgoingPacket appendBoolean(boolean bool) {
        return this.appendBoolean(bool, StringEmpty);
    }

    public OutgoingPacket appendBoolean(boolean bool, String debugContext) {
        try {
            this.stream.writeBoolean(bool);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public OutgoingPacket appendDouble(double d) {
        return this.appendDouble(d, StringEmpty);
    }

    public OutgoingPacket appendDouble(double d, String debugContext) {
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

    public OutgoingPacket appendShort(final int value) {
        return this.appendShort(value, StringEmpty);
    }

    public OutgoingPacket appendShort(final int value, final String debugContext) {
        try {
            this.stream.writeShort(value);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return this;
    }
}