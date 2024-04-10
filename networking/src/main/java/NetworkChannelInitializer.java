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
        context = SSLCertificateLoader.getContext();
        isSSL = context != null;
        config = WebSocketServerProtocolConfig.newBuilder()
                .websocketPath("/")
                .checkStartsWith(true)
                .maxFramePayloadLength(MAX_FRAME_SIZE)
                .build();
    }

    @Override
    public void initChannel(SocketChannel ch) {
        assert incomingPacketLogger != null;
        assert packetParser != null;
        ch.pipeline().addLast("logger", new LoggingHandler());

        if (isSSL) {
            ch.pipeline().addLast(context.newHandler(ch.alloc()));
        }
        ch.pipeline().addLast("httpCodec", new HttpServerCodec());
        ch.pipeline().addLast("objectAggregator", new HttpObjectAggregator(MAX_FRAME_SIZE));
        ch.pipeline().addLast("protocolHandler", new WebSocketServerProtocolHandler(config));
        ch.pipeline().addLast("websocketCodec", new WebSocketCodec());

        ch.pipeline().addLast(new GamePolicyDecoder());
        ch.pipeline().addLast(new GameByteFrameDecoder());
        ch.pipeline().addLast(gameByteDecoder);

        if (packetManager.isParallelParsingEnabled()) {
            ch.pipeline().addLast(incomingPacketLogger);
        }

        ch.pipeline().addLast(packetParser);
        
        ch.pipeline().addLast(new OutgoingPacketEncoder());
        if (packetManager.isParallelParsingEnabled()) {
            ch.pipeline().addLast(outgoingPacketLogger);
        }
    }

    public boolean isSSL() {
        return isSSL;
    }
}
