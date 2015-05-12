package org.ms.iknow.crawler;

/**
 * http://andreas-hess.info/programming/webcrawler/src/ie/moguntia/threads/ControllableThread.java
 */
abstract public class ControllableThread extends Thread {

    protected ThreadController threadController;
    protected int              id;

    public void setThreadController(ThreadController threadController) {
        this.threadController = threadController;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void run() {
        // do something
        process(null);
        threadController.finished(id);
    }

    /**
     * The thread invokes the process method.
     */
    public abstract void process(Object o);
}
