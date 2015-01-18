package isst.fraunhofer.de.ompi.model;

import java.io.Serializable;

/**
 * Created by Dmytro Urin on 07.01.2015.
 */
public class EarlyMemory implements Serializable {

    private long id;
    private String personId;
    private int dayNr;
    private String memory;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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
        return memory;
    }

    public void setMemory(String memory) {
        memory = memory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public EarlyMemory(String personId, int dayNr, String memory) {
        this.personId = personId;
        this.dayNr = dayNr;
        this.memory = memory;
    }


}
