package org.ms.iknow.language.de.type;

public class Structure {

    String erstePerson;
    String zweitePerson;
    String drittePerson;

    /**
     * @return the erstePerson
     */
    public String getErstePerson() {
        return erstePerson;
    }

    /**
     * @param erstePerson the erstePerson to set
     */
    public void setErstePerson(String erstePerson) {
        this.erstePerson = erstePerson;
    }

    /**
     * @return the zweitePerson
     */
    public String getZweitePerson() {
        return zweitePerson;
    }

    /**
     * @param zweitePerson the zweitePerson to set
     */
    public void setZweitePerson(String zweitePerson) {
        this.zweitePerson = zweitePerson;
    }

    /**
     * @return the drittePerson
     */
    public String getDrittePerson() {
        return drittePerson;
    }

    /**
     * @param drittePerson the drittePerson to set
     */
    public void setDrittePerson(String drittePerson) {
        this.drittePerson = drittePerson;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("erstePerson: " + erstePerson + "\n");
        builder.append("zweitePerson: " + zweitePerson + "\n");
        builder.append("drittePerson: " + drittePerson + "\n");

        return builder.toString();
    }
}
