package packets;

import habbo.internationalization.LocalizedString;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import networking.packets.IPacketWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.function.BiConsumer;

public class PacketWriter implements IPacketWriter {
    private static final String STRING_EMPTY = "";
    private static final Logger LOGGER = LogManager.getLogger();

    private final ByteBufOutputStream stream;
    private final ByteBuf channelBuffer;

    public PacketWriter() {
        this.channelBuffer = Unpooled.buffer();
        this.stream = new ByteBufOutputStream(this.channelBuffer);
    }

    public PacketWriter(ByteBuf buffer) {
        this.channelBuffer = buffer;
        this.stream = new ByteBufOutputStream(this.channelBuffer);
    }

    @Override
    public IPacketWriter appendRawBytes(byte[] bytes) {
        return this.appendRawBytes(bytes, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendRawBytes(byte[] bytes, String debugContext) {
        try {
            this.stream.write(bytes);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    @Override
    public IPacketWriter appendString(LocalizedString localizedString) {
        return this.appendString(localizedString.getKey(), STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendString(String string) {
        return this.appendString(string, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendString(String string, String debugContext) {
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

    @Override
    public IPacketWriter appendChar(int charValue) {
        return this.appendChar(charValue, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendChar(int obj, String debugContext) {
        try {
            this.stream.writeChar(obj);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    @Override
    public IPacketWriter appendInt(Integer integer) {
        return this.appendInt(integer, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendInt(Integer integer, String debugContext) {
        try {
            this.stream.writeInt(integer);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }


    @Override
    public IPacketWriter appendBoolean(boolean bool) {
        return this.appendBoolean(bool, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendBoolean(boolean bool, String debugContext) {
        try {
            this.stream.writeBoolean(bool);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    @Override
    public IPacketWriter appendDouble(double d) {
        return this.appendDouble(d, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendDouble(double d, String debugContext) {
        try {
            this.stream.writeDouble(d);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    @Override
    public ByteBuf getBuffer() {
        this.channelBuffer.setInt(0, this.channelBuffer.writerIndex() - 4);

        return this.channelBuffer.copy();
    }

    @Override
    public IPacketWriter appendShort(final int value) {
        return this.appendShort(value, STRING_EMPTY);
    }

    @Override
    public IPacketWriter appendShort(final int value, final String debugContext) {
        try {
            this.stream.writeShort(value);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return this;
    }

    @Override
    public <T> IPacketWriter appendList(Collection<T> values, BiConsumer<IPacketWriter, T> consumer) {
        this.appendInt(values.size());
        values.forEach(value -> consumer.accept(this, value));
        return this;
    }
}
