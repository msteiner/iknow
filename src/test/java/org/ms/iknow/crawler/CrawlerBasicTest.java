package org.ms.iknow.crawler;

import org.junit.Test;

public class CrawlerBasicTest {

    @Test
    public void testStartAndStopCrawler() {
        ThreadController merger_1 = new ThreadController();
        merger_1.startMergerThread();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
