package isst.fraunhofer.de.ompi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person  implements Serializable {


	private long id;
    private String longId;
    private Integer groupNr;
    private String googleCloudId;
    private boolean hrvMeasurable;



    public boolean isHrvMeasurable() {
        return hrvMeasurable;
    }

    public void setHrvMeasurable(boolean hrvMeasurable) {
        this.hrvMeasurable = hrvMeasurable;
    }




    public String getLongId() {
        return longId;
    }

    public void setLongId(String longId) {
        this.longId = longId;
    }

    public Integer getGroupNr() {
        return groupNr;
    }

    public void setGroupNr(Integer groupNr) {
        this.groupNr = groupNr;
    }


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