package networking.packets;

import habbo.internationalization.LocalizedString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PacketWriter {
    private static final String STRING_EMPTY = "";
    private static final Logger LOGGER = LogManager.getLogger();

    private final ByteBufOutputStream stream;
    private final ByteBuf channelBuffer;

    public PacketWriter() {
        this.channelBuffer = Unpooled.buffer();
        this.stream = new ByteBufOutputStream(this.channelBuffer);
    }

    public PacketWriter appendRawBytes(byte[] bytes) {
        return this.appendRawBytes(bytes, STRING_EMPTY);
    }

    public PacketWriter appendRawBytes(byte[] bytes, String debugContext) {
        try {
            this.stream.write(bytes);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public PacketWriter appendString(LocalizedString localizedString) {
        return this.appendString(localizedString.getKey(), STRING_EMPTY);
    }

    public PacketWriter appendString(String string) {
        return this.appendString(string, STRING_EMPTY);
    }

    public PacketWriter appendString(String string, String debugContext) {
        if (string == null) {
            this.appendString(STRING_EMPTY);
            return this;
        }

        try {
            byte[] data = string.getBytes();
            this.stream.writeShort(data.length);
            this.stream.write(data);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public PacketWriter appendChar(int charValue) {
        return this.appendChar(charValue, STRING_EMPTY);
    }

    public PacketWriter appendChar(int obj, String debugContext) {
        try {
            this.stream.writeChar(obj);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public PacketWriter appendInt(Integer integer) {
        return this.appendInt(integer, STRING_EMPTY);
    }

    public PacketWriter appendInt(Integer integer, String debugContext) {
        try {
            this.stream.writeInt(integer);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }


    public PacketWriter appendBoolean(boolean bool) {
        return this.appendBoolean(bool, STRING_EMPTY);
    }

    public PacketWriter appendBoolean(boolean bool, String debugContext) {
        try {
            this.stream.writeBoolean(bool);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public PacketWriter appendDouble(double d) {
        return this.appendDouble(d, STRING_EMPTY);
    }

    public PacketWriter appendDouble(double d, String debugContext) {
        try {
            this.stream.writeDouble(d);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    public ByteBuf getBuffer() {
        this.channelBuffer.setInt(0, this.channelBuffer.writerIndex() - 4);

        return this.channelBuffer.copy();
    }

    public PacketWriter appendShort(final int value) {
        return this.appendShort(value, STRING_EMPTY);
    }

    public PacketWriter appendShort(final int value, final String debugContext) {
        try {
            this.stream.writeShort(value);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }
}
