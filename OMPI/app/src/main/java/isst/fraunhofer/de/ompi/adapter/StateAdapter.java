package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import isst.fraunhofer.de.ompi.Constants;
import isst.fraunhofer.de.ompi.model.State;

/**
 * Created by durin on 9/1/2015.
 */
public class StateAdapter {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String STATE_FIRST_HRV = "firstHrv";
    public static final String STATE_DAY_NR = "dayNr";
    private static StateAdapter mInstance;
    private static Context mContext;



    private State state;
    SharedPreferences settings;


    public State getState() {
        return state;
    }

    public void inverseFirstHRV(){
        if (state.isFirstHrv())
            state.setFirstHrv(false);
        else
            state.setFirstHrv(true);
        saveState();
    }

    public void nextDay(){
        state.setDayNr(state.getDayNr()+1);
        saveState();
    }

    public void setDayNr(int dayNr){
        state.setDayNr(dayNr);
        saveState();

    }



    private StateAdapter(Context pContext){
        mContext=pContext;
        settings = pContext.getSharedPreferences(PREFS_NAME, 0);
        state = loadState();
    }

    public static StateAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new StateAdapter(pContext);
        }
        mContext=pContext;
        return mInstance;
    }

    private State loadState(){
        State state = new State();
        state.setFirstHrv(settings.getBoolean(STATE_FIRST_HRV, true));
        state.setDayNr(settings.getInt(STATE_DAY_NR, 0));
        return state;
    }

    private void  saveState(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(STATE_FIRST_HRV,this.state.isFirstHrv());
        editor.putInt(STATE_DAY_NR, this.state.getDayNr());
        editor.commit();
    }

    public boolean isLastDay(){
        return (state.getDayNr()== Constants.EXPERIMENT_DURATION);
    }



}
