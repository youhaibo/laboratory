package me.hbyou.application.laboratory.crawler;

import me.hbyou.application.laboratory.LaboratoryApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Tony(YouHaibo)
 * @version 1.0.0 2017-08-06 17:03
 */
public class MoviesCrawlerTest extends LaboratoryApplicationTests {

    @Autowired
    private MoviesCrawler moviesCrawler;

    @Test
    public void testAcquireAmericanDramas(){
        moviesCrawler.acquireAmericanDramas();
    }
}
