package model;


import java.io.Serializable;

public class GoodThing  implements Serializable {


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

    private long id;
	private Cycle cycle;
	private String goodThing;
	private String causality;		

}