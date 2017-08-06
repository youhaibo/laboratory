package me.hbyou.application.laboratory.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tony(YouHaibo)
 * @date 2017-08-06 16:32
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 默认的重试次数
     */
    private static final Integer DEFAULT_RETYR_TIMES = 2;

    /**
     * 默认的重试间隔
     */
    private static final Long DEFAULT_RETRY_INTERVAL = 3000L;

    public static String xmlPostWithRetry(String url, String xmlParams) {
        return xmlPostWithRetry(url, xmlParams, DEFAULT_RETYR_TIMES, DEFAULT_RETRY_INTERVAL);
    }

    /**
     * 带重试机制的http post请求工具
     *
     * @param url        请求的url
     * @param xmlParams  xml格式的参数
     * @param retryTimes 重试次数
     * @param interval   重试时间间隔
     *                   这里实际等待的时间为：interval*retryTimes
     * @return 返回响应内容
     * null代表错误
     */
    public static String xmlPostWithRetry(String url, String xmlParams, int retryTimes, long interval) {
        int i = 0;
        while (i < retryTimes) {
            String response = xmlPost(url, xmlParams);
            if (StringUtils.isNotBlank(response)) {
                return response;
            }

            i++;

            takeABreak(interval * i);
        }

        return null;
    }

    /**
     * post请求支持
     */
    public static String xmlPost(String url, String xmlParams) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpclient = HttpClientFactory.getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        if (StringUtils.isNotBlank(xmlParams)) {
            HttpEntity entity = new ByteArrayEntity(xmlParams.getBytes(StandardCharsets.UTF_8));
            httpPost.setEntity(entity);
        }
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            int statuCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            String responseContent = "";
            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity);

                EntityUtils.consume(responseEntity);
            }
            return responseContent;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static String post(String url) {
        CloseableHttpClient httpclient = HttpClientFactory.getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        String responseContent = "";
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            int statuCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity, "gb2312");

                EntityUtils.consume(responseEntity);
            }
            return responseContent;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return responseContent;
    }

    public static String get(String url) {
        CloseableHttpClient httpclient = HttpClientFactory.getHttpClient();
        HttpGet hhtpGet = new HttpGet(url);
        String responseContent = "";
        try (CloseableHttpResponse response = httpclient.execute(hhtpGet)) {
            int statuCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                responseContent = EntityUtils.toString(responseEntity, "gb2312");

                EntityUtils.consume(responseEntity);
            }
            return responseContent;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return responseContent;
    }

    private static void takeABreak(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}