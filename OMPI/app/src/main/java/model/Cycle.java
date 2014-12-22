package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by durin on 19/12/2014.
 */
public class Cycle  implements Serializable {

    private long id;
    private long date;
    private ArrayList<GoodThing> goodThings = new ArrayList<>(3);
    private Person person;
    private String childMemory;

    public ArrayList<GoodThing> getGoodThings() {
        return goodThings;
    }

    public void setGoodThings(ArrayList<GoodThing> goodThings) {
        this.goodThings = goodThings;
    }

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getChildMemory() {
        return childMemory;
    }

    public void setChildMemory(String childMemory) {
        this.childMemory = childMemory;
    }



    public ArrayList<HRV> getHRV() {
        return HRV;
    }

    public void setHRV(ArrayList<HRV> HRV) {
       // for (HRV hrv:HRV)
       // this.HRV.add(hrv);
        this.HRV = HRV;
    }

    private ArrayList<HRV> HRV = new ArrayList<>(165);
}
