package me.hbyou.application.laboratory.http;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tony(YouHaibo)
 * @date 2017-08-05 15:17
 */
public class HttpClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientFactory.class);

    private static volatile CloseableHttpClient httpClient;

    /**
     * 连接池最大连接数
     */
    private static final Integer MAX_TOTAL = 1000;

    /**
     * 每个route默认的最大连接数
     */
    private static final Integer DEFAULT_MAX_PER_ROUTE = 200;

    /**
     * 从连接池中获取连接的超时时间，超过该时间未拿到可用连接，
     * 会抛出org.apache.http.conn.ConnectionPoolTimeoutException:
     * Timeout waiting for connection from pool
     */
    private static final Integer CONNECTION_REQUEST_TIMEOUT = 3000;
    /**
     * 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
     */
    private static final Integer CONNECT_TIMEOUT = 10000;

    /**
     * 服务器返回数据(response)的时间，超过该时间抛出read timeout
     */
    private static final Integer SOCKET_TIMEOUT = 60000;

    /**
     * 空闲永久连接检查间隔
     * 官方推荐使用这个来检查永久链接的可用性，而不推荐每次请求的时候才去检查
     */
    private static final Integer VALIDATE_AFTER_INACTIVITY = 2000;

    private HttpClientFactory() {
    }

    /**
     * 获取HttpClient对象
     */
    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClientFactory.class) {
                if (httpClient == null) {
                    PoolingHttpClientConnectionManager ppccm = new PoolingHttpClientConnectionManager();
                    ppccm.setMaxTotal(MAX_TOTAL);
                    ppccm.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
                    ppccm.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
                    RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                        .setConnectTimeout(CONNECT_TIMEOUT)
                        .setSocketTimeout(SOCKET_TIMEOUT)
                        .build();

                    httpClient = HttpClients.custom()
                        .setDefaultRequestConfig(requestConfig)
                        .setConnectionManager(ppccm)
                        .build();

                }
            }
        }
        return httpClient;
    }

    public static void close() {
        if (httpClient != null) {
            try {
                httpClient.close();
                httpClient = null;
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }
}
