package me.hbyou.application.laboratory.http;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Tony(YouHaibo)
 * @date 2017-08-05 15:17
 */
public class HttpClientFactory {

    public HttpClient getHttpClient() {

        HttpClient httpClient = HttpClients.createDefault();

        return httpClient;
    }
}
