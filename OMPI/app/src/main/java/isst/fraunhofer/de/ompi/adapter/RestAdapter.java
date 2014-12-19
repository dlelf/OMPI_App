package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import model.Cycle;
import model.Person;

/**
 * Created by durin on 19/12/2014.
 */
public class RestAdapter {
    private Context mContext;
    SharedPreferences settings;
    private static RestAdapter mInstance;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String url = "http://192.168.200.186:8080/people";



    public static RestAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new RestAdapter(pContext);
        }
        return mInstance;
    }

    private RestAdapter(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME, 0);

    }


    public void sendPerson(Person person) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url, person, Person.class);

    }

    public void sendCycle(Cycle cycle){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url, cycle, Cycle.class);

    }


}
