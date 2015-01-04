package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import isst.fraunhofer.de.ompi.adapter.HRVAdapter;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.WebAPI;
import isst.fraunhofer.de.ompi.model.Cycle;
import isst.fraunhofer.de.ompi.model.Greeting;
import isst.fraunhofer.de.ompi.model.HRV;

//import isst.fraunhofer.de.ompi.util.Greeting;



public class Test extends Activity {
    RestAdapter restAdapter;
    HRVAdapter hrvAdapter ;
    Cycle cycle;



    @Override
    protected void onStart() {
        super.onStart();
        final WebAPI webservices = new WebAPI(this);
        Greeting gr = webservices.getService().getPerson();



        hrvAdapter = HRVAdapter.getInstance(this);
        restAdapter=RestAdapter.getInstance(this);
        /*hrvAdapter.checkFiles();



        CycleAdapter cycleAdapter= CycleAdapter.getInstance(this);
        restAdapter = RestAdapter.getInstance(this);
        cycle = new Cycle();
        cycle.setChildMemory("dddd");
        Person person = new Person();
        person.setId(53);
        cycle.setPerson(person);*/


        /*HRV hrv = new HRV();
        hrv.setStep(5);
        cycle.getHRV().add(hrv);
        //cycle =cycleAdapter.createCycle();
        System.out.print("Cycle readed successfull");*/
        new HttpRequestTask().execute();






        //new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Cycle> {
        @Override
        protected Cycle doInBackground(Void... params) {
            try {
                ArrayList<HRV> measurement = hrvAdapter.getHRV();
                for (HRV hrv : measurement) {
                    restAdapter.sendHRV(hrv);
                    Log.i("Start", "Sending hrv:" + hrv.getRrInterval());
                }
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
           return cycle;
        }

    }

    public void nextStep(){
        Intent intent = new Intent(this, TemplateActivity.class);
        startActivity(intent);

    }
}
