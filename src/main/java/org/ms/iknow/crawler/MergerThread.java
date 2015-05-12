package org.ms.iknow.crawler;

public class MergerThread extends ControllableThread {

    @Override
    public void process(Object arg0) {
        // TODO Auto-generated method stub

        int passed = 0;
        int max = 5000;
        int delay = 500;
        while (passed < max) {
            System.out.println("   +++ " + this.getId() + " runs for " + passed + " millis.");
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