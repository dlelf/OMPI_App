package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {


	private long id;

    public String getLongId() {
        return longId;
    }

    public void setLongId(String longId) {
        this.longId = longId;
    }

    private String longId;


	private Integer groupNr;
	

	private Status status;
	
	/*@OneToMany(mappedBy="person")	
	private List<Cycle>  cycles;*/
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

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