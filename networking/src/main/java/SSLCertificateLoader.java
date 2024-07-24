import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public final class SSLCertificateLoader {
    private static final String filePath = "ssl";
    private static final Logger logger = LogManager.getLogger();

    public static SslContext getContext() {
        SslContext context;
        try {
            context = SslContextBuilder.forServer(new File(STR."\{filePath}\{File.separator}cert.pem"), new File(STR."\{filePath + File.separator}privkey.pem")).build();
        } catch ( Exception e ) {
            logger.info("Unable to load ssl: {}", e.getMessage());
            context = null;
        }
        return context;
    }
}
