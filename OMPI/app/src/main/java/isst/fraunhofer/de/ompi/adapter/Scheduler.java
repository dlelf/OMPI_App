package isst.fraunhofer.de.ompi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import isst.fraunhofer.de.ompi.activities.FirstHRVCheckActivity;
import isst.fraunhofer.de.ompi.activities.HRVCheckActivity;
import isst.fraunhofer.de.ompi.activities.HRVResultActivity;
import isst.fraunhofer.de.ompi.activities.InstructionActivity;
import isst.fraunhofer.de.ompi.activities.LinkActivity;
import isst.fraunhofer.de.ompi.activities.PlaceboTaskActivity;
import isst.fraunhofer.de.ompi.activities.RegistrationActivity;
import isst.fraunhofer.de.ompi.activities.SendRegistrationActivity;
import isst.fraunhofer.de.ompi.activities.StartActivity;
import isst.fraunhofer.de.ompi.activities.TaskActivity;
import isst.fraunhofer.de.ompi.activities.WaitForNextDayActivity;
import isst.fraunhofer.de.ompi.activities.WaitForRegistrationActivity;
import isst.fraunhofer.de.ompi.activities.SendDailyDataActivity;
import isst.fraunhofer.de.ompi.activities.EndActivity;
import isst.fraunhofer.de.ompi.activities.InstallHRVActivity;



/**
 * Created by Dmytro Urin on 26.12.2014.
 */
public class Scheduler {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static Scheduler mInstance;
    private PersonAdapter personAdapter;
    private static Context mContext;
    public static final String PROPERTY_CURRENT_ACTIVITY = "currentActivity";
    public static final String PROPERTY_BEFORE_ACTIVITY = "beforeActivity";
    SharedPreferences settings;
    Class activityBefore;



    private Scheduler(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME, 0);
        personAdapter=PersonAdapter.getInstance(pContext);
    }

    public static Scheduler getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new Scheduler(pContext);
        }
        mContext=pContext;
        return mInstance;
    }

    public static enum Action{


        //Regeln zur Navigation von Activitäten
        StartActivity(StartActivity.class,RegistrationActivity.class,null),

        RegistrationActivity(RegistrationActivity.class,InstallHRVActivity.class,SendRegistrationActivity.class),
        InstallHRVActivity(InstallHRVActivity.class, FirstHRVCheckActivity.class,SendRegistrationActivity.class),
        FirstHRVCheckActivity(FirstHRVCheckActivity.class,SendRegistrationActivity.class,null),
        SendRegistrationActivity(SendRegistrationActivity.class, WaitForRegistrationActivity.class,null),
        WaitForRegistrationActivity(WaitForRegistrationActivity.class, InstructionActivity.class,null),

        InstructionActivity(InstructionActivity.class,HRVCheckActivity.class,TaskActivity.class),

        HRVCheckActivity(HRVCheckActivity.class,HRVResultActivity.class,null),
        HRVResultActivity(HRVResultActivity.class,TaskActivity.class,WaitForNextDayActivity.class),

        PlaceboTaskActivity(PlaceboTaskActivity.class,HRVCheckActivity.class,SendDailyDataActivity.class),
        TaskActivity(TaskActivity.class,HRVCheckActivity.class,SendDailyDataActivity.class),

        SendDailyDataActivity(SendDailyDataActivity.class, WaitForNextDayActivity.class,null),

        WaitForNextDayActivity(WaitForNextDayActivity.class, InstructionActivity.class,EndActivity.class),
        EndActivity(EndActivity.class,LinkActivity.class,null),
        LinkActivity(LinkActivity.class,RegistrationActivity.class,null);

       // TemplateActivity(TemplateActivity.class,StartActivity.class,null);



        private Class<? extends Activity> thisActivity;
        private Class<? extends Activity> nextActivity;
        private Class<? extends Activity> alternativeActivity;

        public Class<? extends Activity> nextActivity(){
            return nextActivity;
        }
        public Class<? extends Activity> thisActivity(){
            return thisActivity;
        }
        public Class<? extends Activity> alternativeActivity(){
            return  alternativeActivity;
        }


        Action (Class<? extends Activity> thisActivity, Class<? extends Activity> nextActivity,Class<? extends Activity> alternativeActivity){
            this.thisActivity=thisActivity;
            this.nextActivity=nextActivity;
            this.alternativeActivity=alternativeActivity;
        }


    }

    public Class chooseNextActivity(Activity activity){
        return chooseNextActivity(activity, false);

    }

    public Class chooseNextActivity(Activity activity, boolean alternative){
        Class nextActivity;
        if (!alternative)
            nextActivity = Action.valueOf(activity.getClass().getSimpleName()).nextActivity;
        else
            nextActivity = Action.valueOf(activity.getClass().getSimpleName()).alternativeActivity;
        saveLastActivity(nextActivity);
        saveBeforeActivity(activity.getClass());
        return nextActivity;
    }

    public void changeToActivity(Class<? extends Activity> thisActivity,Class<? extends Activity> nextActivity){
        saveBeforeActivity(thisActivity);
        saveLastActivity(nextActivity);
    }


    public void saveLastActivity(Class activity){
        String className = activity.getSimpleName();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PROPERTY_CURRENT_ACTIVITY,className);
        editor.commit();
    }

    public void saveBeforeActivity(Class activity){
        activityBefore=activity;
        String className = activity.getSimpleName();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PROPERTY_BEFORE_ACTIVITY,className);
        editor.commit();
    }

    public Class chooseBeforeActivity(Activity activity){
        Class nextActivity = getActivityBefore();
        saveLastActivity(nextActivity);
        saveBeforeActivity(activity.getClass());
        return  nextActivity;
    }

    public Class getActivityBefore(){
       if(activityBefore!=null)
           return activityBefore;
        else {
           String className = settings.getString(PROPERTY_BEFORE_ACTIVITY, Action.StartActivity.name());
           Action a = Action.valueOf(className);
           Class r = a.thisActivity;
           return Action.valueOf(className).thisActivity;
       }
    }

    public void setStartActivity(Class<? extends Activity> activity){
        saveLastActivity(activity);
    }


    public Class getLastActivity(){
        String className=settings.getString(PROPERTY_CURRENT_ACTIVITY,Action.RegistrationActivity.name());
        Action a =  Action.valueOf(className);
        Class r=a.thisActivity;
        return Action.valueOf(className).thisActivity;

    }



}
