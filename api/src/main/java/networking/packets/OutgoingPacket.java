package networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class OutgoingPacket {

    private static final Logger logger = LogManager.getLogger();
    private boolean initialized;

    private int header;
    private AtomicInteger refs;
    private ByteBufOutputStream stream;
    private ByteBuf channelBuffer;

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

    public void appendRawBytes(byte[] bytes) {
        try {
            this.stream.write(bytes);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendString(String obj) {
        if (obj == null) {
            this.appendString("");
            return;
        }

        try {
            byte[] data = obj.getBytes();
            this.stream.writeShort(data.length);
            this.stream.write(data);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendChar(int obj) {
        try {
            this.stream.writeChar(obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendChars(Object obj) {
        try {
            this.stream.writeChars(obj.toString());
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendInt(Integer obj) {
        try {
            this.stream.writeInt(obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendInt(Short obj) {
        this.appendShort(0);
        this.appendShort(obj);
    }

    public void appendInt(Byte obj) {
        try {
            this.stream.writeInt((int) obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendInt(Boolean obj) {
        try {
            this.stream.writeInt(obj ? 1 : 0);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendShort(int obj) {
        try {
            this.stream.writeShort((short) obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendByte(Integer b) {
        try {
            this.stream.writeByte(b);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendBoolean(Boolean obj) {
        try {
            this.stream.writeBoolean(obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendDouble(double d) {
        try {
            this.stream.writeDouble(d);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void appendDouble(Double obj) {
        try {
            this.stream.writeDouble(obj);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public OutgoingPacket appendResponse(OutgoingPacket obj) {
        try {
            this.stream.write(obj.getBuffer().array());
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