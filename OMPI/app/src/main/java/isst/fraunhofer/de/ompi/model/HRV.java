package isst.fraunhofer.de.ompi.model;

import java.io.Serializable;

/**
 * Created by durin on 19/12/2014.
 */
public class HRV  implements Serializable {

    private long id;
    private long date;
    private long rrInterval;
    private boolean firstHRV;
    private String personId;
    private int dayNr;


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRrInterval() {
        return rrInterval;
    }

    public void setRrInterval(long rrInterval) {
        this.rrInterval = rrInterval;
    }

    public boolean isFirstHRV() {
        return firstHRV;
    }

    public void setFirstHRV(boolean firstHRV) {
        this.firstHRV = firstHRV;
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


}
