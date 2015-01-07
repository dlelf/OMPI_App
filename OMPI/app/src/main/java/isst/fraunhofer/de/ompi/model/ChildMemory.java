package isst.fraunhofer.de.ompi.model;

import java.io.Serializable;

/**
 * Created by Dmytro Urin on 07.01.2015.
 */
public class ChildMemory implements Serializable {

    private long id;
    private String personId;
    private int dayNr;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public int getDayNr() {
        return dayNr;
    }

    public void setDayNr(int dayNr) {
        this.dayNr = dayNr;
    }

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String memory) {
        Memory = memory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String Memory;

    public ChildMemory(String personId, int dayNr, String memory) {
        this.personId = personId;
        this.dayNr = dayNr;
        Memory = memory;
    }
}
