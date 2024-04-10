package networking.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import networking.util.GameServerAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import utils.security.Sanitizer;

public class IncomingPacket {
    public static final int DEFAULT_MAX_STRING_LENGTH = 350;
    private final Logger logger = LogManager.getLogger();
    private final int header;
    private final ByteBuf buffer;
    private final ChannelHandlerContext ctx;

    public IncomingPacket(int messageId, @NotNull ByteBuf buffer, ChannelHandlerContext ctx) {
        this.header = messageId;
        this.buffer = ((buffer.readableBytes() == 0) ? Unpooled.EMPTY_BUFFER : buffer);
        this.ctx = ctx;
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


    public String readString(@Nullable Cleaner cleaner, final short maxLength) {
        final var unsecureLength = this.readShort();
        final var secureLength = Math.min(unsecureLength, maxLength);
        final var data = new byte[secureLength];
        this.buffer.readBytes(data);
        if (unsecureLength > secureLength) {
            logger.warn("client {} tried to send string larger than {} in packet header {}",
                    ctx.hasAttr(GameServerAttributes.CLIENT)
                            ? ctx.attr(GameServerAttributes.CLIENT).get().getHabbo().getData().getUsername()
                            : ctx.channel().id(), maxLength, this.header);

            this.buffer.skipBytes(unsecureLength - secureLength);
        }

        final var unsecureValue = new String(data);
        if (cleaner != null)
            return cleaner.clean(Jsoup.parse(unsecureValue)).text();
        return unsecureValue;
    }

    public String readString() {
        return readString(Sanitizer.PlainText, (short) DEFAULT_MAX_STRING_LENGTH);
    }

    public int getReadableBytes() {
        return this.buffer.readableBytes();
    }

    public boolean release() {
        return this.buffer.release();
    }

    public String readString(final short maxLength) {
        return this.readString(Sanitizer.PlainText, maxLength);
    }
}