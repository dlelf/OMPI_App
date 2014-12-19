package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person  implements Serializable {


	private long id;

    public String getLongId() {
        return longId;
    }

    public void setLongId(String longId) {
        this.longId = longId;
    }

    private String longId;


	private Integer groupNr;

	private List<Cycle> cycles;

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