package isst.fraunhofer.de.ompi;

import retrofit.RestAdapter;

/**
 * Created by durin on 22/12/2014.
 */
public class Constants {

    public static final String REST_ENDPOINT = "http://153.96.23.210:8080";
    //public static final String REST_ENDPOINT = "http://192.168.200.151:8080";
    public static final RestAdapter.LogLevel REST_LOGLEVEVL = RestAdapter.LogLevel.BASIC;
    public static final int EXPERIMENT_DURATION = 14;
}
