package packets.incoming;

import com.google.inject.Inject;
import core.security.IScriptManager;
import core.security.SecurityBreachLevel;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import networking.packets.IIncomingPacket;
import networking.util.GameNetowrkingAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import utils.security.Sanitizer;

public class IncomingPacket implements IIncomingPacket {
    private final Logger logger = LogManager.getLogger();
    private final int header;
    private final ByteBuf buffer;
    private final ChannelHandlerContext ctx;

    @Inject
    IScriptManager scriptManager;

    public IncomingPacket(int messageId, @NotNull ByteBuf buffer, ChannelHandlerContext ctx) {
        this.header = messageId;
        this.buffer = ((buffer.readableBytes() == 0) ? Unpooled.EMPTY_BUFFER : buffer);
        this.ctx = ctx;
    }

    @Override
    public ByteBuf getBuffer() {
        return this.buffer;
    }

    @Override
    public int getHeader() {
        return this.header;
    }


    @Override
    public int readShort() {
        return this.buffer.readShort();
    }

    @Override
    public Integer readInt() {
        return this.buffer.readInt();
    }

    @Override
    public boolean readBoolean() {
        return this.buffer.readByte() == 1;
    }


    @Override
    public String readString(@Nullable Cleaner cleaner, final short maxLength) {
        final var unsecureLength = this.readShort();
        final var secureLength = Math.min(unsecureLength, maxLength);
        final var data = new byte[secureLength];
        this.buffer.readBytes(data);
        if (unsecureLength > secureLength) {
            final var level = unsecureLength * 3 > maxLength ? SecurityBreachLevel.HIGH : SecurityBreachLevel.MEDIUM;
            final var connectionId = ctx.hasAttr(GameNetowrkingAttributes.CLIENT)
                    ? ctx.attr(GameNetowrkingAttributes.CLIENT).get().getHabbo().getData().getUsername()
                    : ctx.channel().id();
            this.scriptManager.report(level, "client {} tried to send string larger than {} in packet header {}", connectionId, maxLength, this.header);
            this.buffer.skipBytes(unsecureLength - secureLength);
        }

        final var unsecureValue = new String(data);
        if (cleaner != null)
            return cleaner.clean(Jsoup.parse(unsecureValue)).text();
        return unsecureValue;
    }

    @Override
    public String readString() {
        return readString(Sanitizer.PlainText, (short) DEFAULT_MAX_STRING_LENGTH);
    }

    @Override
    public int getReadableBytes() {
        return this.buffer.readableBytes();
    }

    @Override
    public boolean release() {
        return this.buffer.release();
    }

    @Override
    public String readString(final short maxLength) {
        return this.readString(Sanitizer.PlainText, maxLength);
    }
}