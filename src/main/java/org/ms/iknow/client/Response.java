package org.ms.iknow.client;

import java.util.Set;

public class Response {

    MetaData    metaData;
    Set<Synset> synsets;

    /**
     * @return the metaData
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * @return the synsets
     */
    public Set<Synset> getSynsets() {
        return synsets;
    }

    /**
     * @param metaData the metaData to set
     */
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * @param synsets the synsets to set
     */
    public void setSynsets(Set<Synset> synsets) {
        this.synsets = synsets;
    }
}
