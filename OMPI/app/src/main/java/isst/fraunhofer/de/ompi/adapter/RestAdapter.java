package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import isst.fraunhofer.de.ompi.Constants;
import isst.fraunhofer.de.ompi.model.EarlyMemory;
import isst.fraunhofer.de.ompi.model.GoodThing;
import isst.fraunhofer.de.ompi.model.HRV;
import isst.fraunhofer.de.ompi.model.Person;

/**
 * Created by durin on 19/12/2014.
 */
public class RestAdapter {
    private static Context mContext;
    SharedPreferences settings;
    private static RestAdapter mInstance;
    public static final String PREFS_NAME = "MyPrefsFile";
    DateFormat dateFormat;
    RestTemplate restTemplate;
    Calendar cal;




    public static RestAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new RestAdapter(pContext);
        }
        mContext=pContext;
        return mInstance;
    }

    private RestAdapter(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME, 0);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        cal = Calendar.getInstance();
       // System.setProperty("http.keepAlive", "false");

        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

    }


    public void sendPerson(Person person) {
        person.setDate(dateFormat.format(cal.getTime()));
        restTemplate.postForObject(Constants.REST_ENDPOINT+"/people", person, Person.class);
    }


    public void sendHRV(HRV hrv){
        restTemplate.postForObject(Constants.REST_ENDPOINT+"/hrv", hrv, HRV.class);
    }

    public void sendHRVs(ArrayList<HRV> hrvs){

        for (HRV hrv : hrvs) {
            restTemplate.postForObject(Constants.REST_ENDPOINT + "/hrv", hrv, HRV.class);
        }
    }


    public void sendGoodThings(ArrayList<GoodThing> goodThings) {
        for(GoodThing goodThing : goodThings) {
            goodThing.setDate(dateFormat.format(cal.getTime()));
            restTemplate.postForObject(Constants.REST_ENDPOINT + "/GoodThing", goodThing, GoodThing.class);
        }
    }


    public void sendEarlyMemory(EarlyMemory earlyMemory){
        earlyMemory.setDate(dateFormat.format(cal.getTime()));
        restTemplate.postForObject(Constants.REST_ENDPOINT+"/EarlyMemory", earlyMemory, EarlyMemory.class);

    }


}
