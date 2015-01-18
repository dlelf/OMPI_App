package isst.fraunhofer.de.ompi.model;


import java.io.Serializable;

public class GoodThing  implements Serializable {

    private long id;
    private String goodThing;
    private String causality;
    private String personId;
    private int dayNr;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getGoodThing() {
        return goodThing;
    }

    public void setGoodThing(String goodThing) {
        this.goodThing = goodThing;
    }

    public String getCausality() {
        return causality;
    }

    public void setCausality(String causality) {
        this.causality = causality;
    }



}