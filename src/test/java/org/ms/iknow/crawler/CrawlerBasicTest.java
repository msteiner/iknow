package org.ms.iknow.crawler;

import org.junit.Test;

public class CrawlerBasicTest {
  
  @Test
  public void testStartAndStopCrawler() {
    ThreadController threadController = new ThreadController();
    threadController.startThread();
    try {
        Thread.sleep(6000);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
}
