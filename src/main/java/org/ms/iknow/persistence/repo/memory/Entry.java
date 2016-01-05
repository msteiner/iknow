package org.ms.iknow.persistence.repo.memory;

public abstract class Entry {

    private boolean           locked = false;
    private static final long TIME_OUT = 5000L;
    private static final long DELAY    = 2L;

    public void lock() {
        int delay = 0;
        while (delay < TIME_OUT) {
            if (this.locked) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                delay += DELAY;
            } else {
                this.locked = true;
                return;
            }
        }
        System.out.println("\n   +++ ERROR: Timeout while try to lock entry!\n");
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        return this.locked;
    }
}
