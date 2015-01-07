package isst.fraunhofer.de.ompi.model;

import java.io.Serializable;

/**
 * Created by durin on 19/12/2014.
 */
public class HRV  implements Serializable {

    private long id;
    private long date;
    private long rrInterval;
    private boolean beforeMeasurement;
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

    public boolean isBeforeReading() {
        return beforeMeasurement;
    }

    public void setBeforeReading(boolean beforeReading) {
        this.beforeMeasurement = beforeReading;
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
