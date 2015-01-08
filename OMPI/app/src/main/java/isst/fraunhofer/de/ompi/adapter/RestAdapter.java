package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import isst.fraunhofer.de.ompi.Constants;
import isst.fraunhofer.de.ompi.model.ChildMemory;
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
    public static final String url = "http://192.168.200.154:8080";




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

    }


    public void sendPerson(Person person) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(Constants.REST_ENDPOINT+"/people", person, Person.class);

    }

   /* public void sendCycle(Cycle cycle){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url+"/cycle", cycle, Cycle.class);

    }*/
    public void sendHRV(HRV hrv){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url+"/HRV", hrv, HRV.class);

    }

    public void sendHRVs(ArrayList<HRV> hrvs){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        for (HRV hrv : hrvs) {
            restTemplate.postForObject(url + "/HRV", hrv, HRV.class);
        }
    }

    public void sendGoodThing(GoodThing goodThing) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url + "/goodThing", goodThing, GoodThing.class);
    }

    public void sendChildMemory(ChildMemory childMemory){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.postForObject(url+"/childMemory",childMemory, ChildMemory.class);

    }


}
