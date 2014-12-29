package isst.fraunhofer.de.ompi.adapter;

import isst.fraunhofer.de.ompi.model.Greeting;
import retrofit.http.GET;

/**
 * Created by durin on 22/12/2014.
 */
public interface WebInterface {

    @GET("/pers")
    Greeting getPerson();
}
