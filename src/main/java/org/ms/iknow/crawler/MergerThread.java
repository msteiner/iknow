package org.ms.iknow.crawler;

/**
 * see http://andreas-hess.info/programming/webcrawler/src/ie/moguntia/webcrawler/PSuckerThread.java
 * 
 * 
 */
public class MergerThread extends ControllableThread {

    /**
     * IF ((obj_1.name == obj_2.name) AND
     *     (synapse == null))
     * THEN ask for (isIdentical)
     */
    @Override
    public void process() {

    }

    public void processOld(Object arg0) {

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