package isst.fraunhofer.de.ompi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import isst.fraunhofer.de.ompi.activities.HRVCheckActivity;
import isst.fraunhofer.de.ompi.activities.RegistrationActivity;
import isst.fraunhofer.de.ompi.activities.SendRegistrationActivity;
import isst.fraunhofer.de.ompi.activities.StartActivity;
import isst.fraunhofer.de.ompi.activities.TemplateActivity;
import isst.fraunhofer.de.ompi.activities.WaitForRegistrationActivity;
import isst.fraunhofer.de.ompi.activities.InstructionActivity;
import isst.fraunhofer.de.ompi.activities.LinkActivity;
import isst.fraunhofer.de.ompi.activities.HRVResultActivity;

/**
 * Created by Dmytro Urin on 26.12.2014.
 */
public class Scheduler {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static Scheduler mInstance;
    private Context mContext;
    public static final String PROPERTY_CURRENT_ACTIVITY = "currentActivity";
    SharedPreferences settings;


    private Scheduler(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME, 0);
    }

    public static Scheduler getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new Scheduler(pContext);
        }
        return mInstance;
    }

    public static enum Action{


        //Regeln zur Navigation von Activitäten
        StartActivity(StartActivity.class,RegistrationActivity.class),
        RegistrationActivity(RegistrationActivity.class,LinkActivity.class),
        LinkActivity(LinkActivity.class,HRVCheckActivity.class),
        HRVCheckActivity(HRVCheckActivity.class,SendRegistrationActivity.class),
        SendRegistrationActivity(SendRegistrationActivity.class, WaitForRegistrationActivity.class),
        WaitForRegistrationActivity(WaitForRegistrationActivity.class, InstructionActivity.class),
        InstructionActivity(InstructionActivity.class,TemplateActivity.class),

        HRVResultActivity(HRVResultActivity.class,TemplateActivity.class),



        TemplateActivity(TemplateActivity.class,StartActivity.class);



        private Class<? extends Activity> thisActivity;
        private Class<? extends Activity> nextActivity;

        public Class<? extends Activity> nextActivity(){
            return nextActivity;
        }
        public Class<? extends Activity> thisActivity(){
            return thisActivity;
        }


        Action (Class<? extends Activity> thisActivity, Class<? extends Activity> nextActivity){
            this.thisActivity=thisActivity;
            this.nextActivity=nextActivity;
        }


    }

    public Class chooseNextActivity(Activity activity){
        Class nextActivity = Action.valueOf(activity.getClass().getSimpleName()).nextActivity;
        saveLastActivity(nextActivity);
        return nextActivity;
    }

    public void saveLastActivity(Class activity){
        String className = activity.getSimpleName();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PROPERTY_CURRENT_ACTIVITY,className);
        editor.commit();
    }

    public void setStartActivity(Class<? extends Activity> activity){
        saveLastActivity(activity);
    }


    public Class getLastActivity(){
        String className=settings.getString(PROPERTY_CURRENT_ACTIVITY,Action.StartActivity.name());
        Action a =  Action.valueOf(className);
        Class r=a.thisActivity;
        return Action.valueOf(className).thisActivity;

    }



}
