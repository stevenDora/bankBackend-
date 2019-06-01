package cn.stylefeng.guns.modular.system.utils.http;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class SSLSelfSigned {
    public static final SSLConnectionSocketFactory SSL_CONNECTION_SOCKET_FACTORY;
    protected static final Logger logger = LoggerFactory.getLogger(SSLSelfSigned.class);

    static {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom().loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE).build();
        } catch (KeyManagementException e) {
            logger.error("{}", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("{}", e);
        } catch (KeyStoreException e) {
            logger.error("{}", e);
        }
        SSL_CONNECTION_SOCKET_FACTORY = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);
    }

    private SSLSelfSigned() {
    }
}
