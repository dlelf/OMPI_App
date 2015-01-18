package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import isst.fraunhofer.de.ompi.model.Person;

/**
 * Created by durin on 18/12/2014.
 */
 public  class PersonAdapter {
    private static PersonAdapter mInstance;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_PERSON_ID = "personId";
    public static final String PROPERTY_GROUP_NR = "groupNr";
    public static final String PROPERTY_PERSON_SEX = "person_sex";
    public static final String PROPERTY_HRV_MEASURABLE= "hrvMeasurable";

    private static Context mContext;
    SharedPreferences settings;
    private Person person;


    private PersonAdapter(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME,0);
        person =loadPerson();
    }

    public static PersonAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new PersonAdapter(pContext);
        }
        mContext=pContext;
        return mInstance;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGoogleId(String googleId){
        this.getPerson().setGoogleCloudId(googleId);
        saveAll();
    }


    private Person loadPerson(){
        Person person = new Person();
        person.setLongId( settings.getString(PROPERTY_PERSON_ID,""));
        person.setGoogleCloudId(settings.getString( PROPERTY_REG_ID,""));
        person.setHrvMeasurable(settings.getBoolean( PROPERTY_HRV_MEASURABLE,false));
        person.setSex(settings.getString( PROPERTY_PERSON_SEX,""));
        person.setGroupNr(settings.getInt( PROPERTY_GROUP_NR,0));
        return person;
    }

    public void saveAll(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PROPERTY_PERSON_ID,person.getLongId());
        editor.putString(PROPERTY_REG_ID,person.getGoogleCloudId());
        editor.putBoolean(PROPERTY_HRV_MEASURABLE,person.isHrvMeasurable());
        editor.putString(PROPERTY_PERSON_SEX,person.getSex());
        editor.putInt(PROPERTY_GROUP_NR,person.getGroupNr());
        editor.commit();

    }

    public void setGroupId(int groupNr){
        person.setGroupNr(groupNr);
        saveAll();
    }



}
