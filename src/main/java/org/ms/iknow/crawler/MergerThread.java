package org.ms.iknow.crawler;

/**
 * see http://andreas-hess.info/programming/webcrawler/src/ie/moguntia/webcrawler/PSuckerThread.java
 * 
 * 
 */
public class MergerThread extends ControllableThread {

    @Override
    public void process(Object arg0) {

        int passed = 0;
        int max = 5000;
        int delay = 1000;
        while (passed < max) {
            System.out.println("   +++ " + Thread.currentThread().getId() + " runs for " + passed + " millis.");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            passed = passed + delay;
        }
    }
}