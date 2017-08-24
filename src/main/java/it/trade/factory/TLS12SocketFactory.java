package it.trade.factory;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enables TLS v1.2 when creating SSLSockets.
 * <p/>
 * For some reason, android supports TLS v1.2 from API 16, but enables it by
 * default only from API 20.
 * @link https://developer.android.com/reference/javax/net/ssl/SSLSocket.html
 * @see SSLSocketFactory
 */
public class TLS12SocketFactory extends SSLSocketFactory {
    private static final String[] TLS_V12_ONLY = {"TLSv1.2"};
    private static final Logger LOGGER = LoggerFactory.getLogger(TLS12SocketFactory.class);

    final SSLSocketFactory delegate;

    public TLS12SocketFactory(SSLSocketFactory base) {
        this.delegate = base;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return patch(delegate.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return patch(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return patch(delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return patch(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return patch(delegate.createSocket(address, port, localAddress, localPort));
    }

    private Socket patch(Socket s) {
        if (s instanceof SSLSocket) {
            ((SSLSocket) s).setEnabledProtocols(TLS_V12_ONLY);
        }
        return s;
    }

    public static void enableTLS12(OkHttpClient.Builder client) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            X509TrustManager trustManager = getTrustManager();
            sslContext.init(null, new TrustManager[] { trustManager }, null);

            client.sslSocketFactory(new TLS12SocketFactory(sslContext.getSocketFactory()), trustManager);

            ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .build();

            List<ConnectionSpec> specs = new ArrayList<>();
            specs.add(cs);
            specs.add(ConnectionSpec.COMPATIBLE_TLS);
            specs.add(ConnectionSpec.CLEARTEXT);

            client.connectionSpecs(specs);
        } catch (Exception exc) {
            LOGGER.error("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
        }
    }

    private static X509TrustManager getTrustManager() {
        X509TrustManager trustManager = null;
        try {
            TrustManagerFactory  trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }

            trustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            LOGGER.error("getTrustManager", "Error while getting a  X509TrustManager", e);
        }
        return trustManager;
    }
}