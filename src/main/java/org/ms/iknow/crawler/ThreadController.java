package org.ms.iknow.crawler;

/**
 * http://andreas-hess.info/programming/webcrawler/src/ie/moguntia/threads/ThreadController.java 
 */
public class ThreadController {
  
    public void startThread() {
        ControllableThread thread = new MergerThread();
        thread.setId(100);
        thread.setName("Thread 100");
        thread.setThreadController(this);
        thread.start();
    }
  
    public synchronized void finished(int threadId) {
        // TODO implement something.
        System.out.println("   +++ Thread " + threadId + " finished.");
    }
}