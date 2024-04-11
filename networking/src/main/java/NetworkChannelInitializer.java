import codec.WebSocketCodec;
import com.google.inject.Inject;
import decoders.*;
import encoders.OutgoingPacketEncoder;
import encoders.OutgoingPacketLogger;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import networking.packets.IPacketManager;


public class NetworkChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_FRAME_SIZE = 500000;

    private final SslContext context;
    private final boolean isSSL;
    private final WebSocketServerProtocolConfig config;

    @Inject private IncomingPacketLogger incomingPacketLogger;
    @Inject
    private OutgoingPacketLogger outgoingPacketLogger;
    @Inject
    private PacketParser packetParser;
    @Inject
    private IPacketManager packetManager;

    @Inject
    private GameByteDecoder gameByteDecoder;
    public NetworkChannelInitializer() {
        this.context = SSLCertificateLoader.getContext();
        this.isSSL = this.context != null;
        this.config = WebSocketServerProtocolConfig.newBuilder()
                .websocketPath("/")
                .checkStartsWith(true)
                .maxFramePayloadLength(MAX_FRAME_SIZE)
                .build();
    }

    @Override
    public void initChannel(SocketChannel ch) {
        assert this.incomingPacketLogger != null;
        assert this.packetParser != null;
        ch.pipeline().addLast("logger", new LoggingHandler());

        if (this.isSSL) {
            ch.pipeline().addLast(this.context.newHandler(ch.alloc()));
        }
        ch.pipeline().addLast("httpCodec", new HttpServerCodec());
        ch.pipeline().addLast("objectAggregator", new HttpObjectAggregator(MAX_FRAME_SIZE));
        ch.pipeline().addLast("protocolHandler", new WebSocketServerProtocolHandler(this.config));
        ch.pipeline().addLast("websocketCodec", new WebSocketCodec());

        ch.pipeline().addLast(new GamePolicyDecoder());
        ch.pipeline().addLast(new GameByteFrameDecoder());
        ch.pipeline().addLast(this.gameByteDecoder);

        if (this.packetManager.isLoggingEnabled()) {
            ch.pipeline().addLast(this.incomingPacketLogger);
        }

        ch.pipeline().addLast(this.packetParser);
        
        ch.pipeline().addLast(new OutgoingPacketEncoder());
        if (this.packetManager.isLoggingEnabled()) {
            ch.pipeline().addLast(this.outgoingPacketLogger);
        }
    }

    public boolean isSSL() {
        return this.isSSL;
    }
}
