package org.ms.iknow.crawler;

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
