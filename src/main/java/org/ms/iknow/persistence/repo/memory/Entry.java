package org.ms.iknow.persistence.repo.memory;

public abstract class Entry {

    private boolean           isLocked = false;
    private static final long TIME_OUT = 5000L;
    private static final long DELAY    = 2L;

    public void lock() {
        int delay = 0;
        while (delay < TIME_OUT) {
            if (isLocked) {
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                delay += DELAY;
            } else {
                isLocked = true;
                return;
            }
        }
        System.out.println("\n   +++ ERROR: Timeout while try to lock entry!\n");
    }

    public void unlock() {
        isLocked = false;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
