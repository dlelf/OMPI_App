package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import model.Person;

/**
 * Created by durin on 18/12/2014.
 */
 public  class PersonAdapter {
    private static PersonAdapter mInstance;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PROPERTY_REG_ID = "registration_id";

    private Context mContext;
    SharedPreferences settings;
    private Person person;


    private PersonAdapter(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME, 0);
        person =loadPerson();
    }

    public static PersonAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new PersonAdapter(pContext);
        }
        return mInstance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Person loadPerson(){
        Person person = new Person();

        person.setLongId( settings.getString("personId",""));
        person.setGoogleCloudId(settings.getString( PROPERTY_REG_ID,""));


        return person;

    }

    public void saveAll(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("personId",person.getLongId());
        editor.putString(PROPERTY_REG_ID,person.getGoogleCloudId());
        editor.commit();

    }



}
