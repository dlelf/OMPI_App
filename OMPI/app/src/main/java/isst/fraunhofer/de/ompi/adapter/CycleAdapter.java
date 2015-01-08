package isst.fraunhofer.de.ompi.adapter;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by durin on 19/12/2014.
 */
public class CycleAdapter {
    private static Context mContext;
    private static CycleAdapter mInstance;
    private HRVAdapter hrvAdapter;
    private PersonAdapter personAdapter;
   // private Cycle mCycle;


    public CycleAdapter (Context context){
        mContext=context;
        hrvAdapter=HRVAdapter.getInstance(context);
        personAdapter=PersonAdapter.getInstance(context);
    }

   /* public  Cycle createCycle() {
        Cycle cycle = new Cycle();
        cycle.setPerson(personAdapter.getPerson());
        cycle.setDate(getCurrentDate());
        hrvAdapter.setCycle(cycle);
        //cycle.setHRV(hrvAdapter.getHRV());
        cycle.getHRV().add(hrvAdapter.getHRV().get(0));
        mCycle = cycle;
        return cycle;

    }*/



    public long getCurrentDate()
    {
        Date date= new Date();
        date.setTime(System.currentTimeMillis());
        String format = "yyyyMMddhhmmssS";
        SimpleDateFormat sdf =new SimpleDateFormat(format);
        String d =sdf.format(date);
        return Long.parseLong(d);
    }

    public static CycleAdapter getInstance(Context pContext){
        if (mInstance == null) {
            mInstance = new CycleAdapter(pContext);
        }
        mContext=pContext;
        return mInstance;
    }

   /* public Cycle getCycle() {
        return mCycle;
    }*/

}
