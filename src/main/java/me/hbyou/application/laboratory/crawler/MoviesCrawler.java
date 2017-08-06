package me.hbyou.application.laboratory.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Tony(YouHaibo)
 * @version 1.0.0 2017-08-06 16:48
 */
@Component
public class MoviesCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoviesCrawler.class);

    public void acquireAmericanDramas() {
        String url = "http://www.meijutt.com/";

        try {
            Document document = Jsoup.connect(url).get();

            System.out.println("xxx");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
