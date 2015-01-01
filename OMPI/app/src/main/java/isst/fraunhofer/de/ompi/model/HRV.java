package isst.fraunhofer.de.ompi.model;

import java.io.Serializable;

/**
 * Created by durin on 19/12/2014.
 */
public class HRV  implements Serializable {

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

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public long getRrInterval() {
        return rrInterval;
    }

    public void setRrInterval(long rrInterval) {
        this.rrInterval = rrInterval;
    }

    public boolean isBeforeReading() {
        return beforeReading;
    }

    public void setBeforeReading(boolean beforeReading) {
        this.beforeReading = beforeReading;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    private long id;
    private long date;
    private Cycle cycle;
    private long rrInterval;
    private boolean beforeReading;



    private int step;
}
