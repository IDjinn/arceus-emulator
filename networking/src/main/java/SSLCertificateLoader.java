import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public final class SSLCertificateLoader {
    private static final String filePath = "ssl";
    private static final Logger logger = LogManager.getLogger(SSLCertificateLoader.class);

    public static SslContext getContext() {
        try {
            File certFile = new File(filePath + File.separator + "cert.pem");
            File keyFile  = new File(filePath + File.separator + "privkey.pem");
            return SslContextBuilder.forServer(certFile, keyFile).build();
        } catch (Exception e) {
            logger.info("Unable to load ssl: {}", e.getMessage());
            return null;
        }
    }
}