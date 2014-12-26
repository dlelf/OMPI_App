package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person  implements Serializable {


	private long id;
    private int step;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getLongId() {
        return longId;
    }

    public void setLongId(String longId) {
        this.longId = longId;
    }

    private String longId;


    public Integer getGroupNr() {
        return groupNr;
    }

    public void setGroupNr(Integer groupNr) {
        this.groupNr = groupNr;
    }

    public ArrayList<Cycle> getCycles() {
        return cycles;
    }

    public void setCycles(ArrayList<Cycle> cycles) {
        this.cycles = cycles;
    }

    private Integer groupNr;

	private ArrayList<Cycle> cycles= new ArrayList<>(10);

	private String googleCloudId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGoogleCloudId() {
		return googleCloudId;
	}

	public void setGoogleCloudId(String googleCloudId) {
		this.googleCloudId = googleCloudId;
	}
	
	

}