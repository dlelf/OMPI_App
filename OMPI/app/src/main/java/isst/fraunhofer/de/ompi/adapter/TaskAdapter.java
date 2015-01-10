package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;

import java.util.ArrayList;

import isst.fraunhofer.de.ompi.model.EarlyMemory;
import isst.fraunhofer.de.ompi.model.GoodThing;

/**
 * Created by Dmytro Urin on 10.01.2015.
 */
public class TaskAdapter {

    private static TaskAdapter mInstance;
    private static Context mContext;

    static EarlyMemory earlyMemory;



    static ArrayList<GoodThing> goodThings;

    PersonAdapter personAdapter;
    StateAdapter stateAdapter;

    private TaskAdapter(Context pContext) {
        mContext = pContext;
        personAdapter = PersonAdapter.getInstance(pContext);
        stateAdapter=StateAdapter.getInstance(pContext);
        goodThings = new ArrayList<GoodThing>();
    }

    public static TaskAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new TaskAdapter(pContext);
        }
        mContext=pContext;
        return mInstance;
    }

    public void addGoodThing(String goodThingText, String causality){
        GoodThing goodThing = new GoodThing();
        goodThing.setCausality(causality);
        goodThing.setDayNr(stateAdapter.getState().getDayNr());
        goodThing.setPersonId(personAdapter.getPerson().getLongId());
        goodThing.setGoodThing(goodThingText);
        goodThings.add(goodThing);
    }

    public void addEarlyMemory(String memory){
        earlyMemory=new EarlyMemory(personAdapter.getPerson().getLongId(),stateAdapter.getState().getDayNr(),memory);
    }

    public static EarlyMemory getEarlyMemory() {
        return earlyMemory;
    }

    public static ArrayList<GoodThing> getGoodThings() {
        return goodThings;
    }



}
