package org.ms.iknow.core.type;

public abstract class Iterator extends ElementBase {

    protected int level;
    protected int currentIndex;
  
    public abstract Synapse nextSynapse();
  
    public void resetIndex() {
        currentIndex = 0;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
  
    public void increaseCurrentIndex() {
        currentIndex++;
    }
  
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
  
    public void increaseLevel() {
        level++;
    }
  
    public void decreaseLevel() {
        if (level > 0) {
            level--;
        }
    }

    public abstract boolean hasMoreElements();
}
