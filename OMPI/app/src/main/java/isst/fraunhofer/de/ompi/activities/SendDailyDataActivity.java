package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.HRVAdapter;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;
import isst.fraunhofer.de.ompi.model.HRV;


public class SendDailyDataActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
   PersonAdapter personAdapter;
    RestAdapter restAdapter;
    HRVAdapter hrvAdapter ;
    StateAdapter stateAdapter;
    Scheduler scheduler;
    Button nextButton;
    TextView text,title,error;
    boolean connectionFailed;
    Activity context;



    @Override
    protected void onStart() {
        super.onStart();
        personAdapter = PersonAdapter.getInstance(this);
        restAdapter = RestAdapter.getInstance(this);
        scheduler= Scheduler.getInstance(this);
        hrvAdapter = HRVAdapter.getInstance(this);
        stateAdapter= StateAdapter.getInstance(this);
        context=this;
        //new HttpRequestTask().execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddata);
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        error = (TextView)this.findViewById(R.id.error);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();

            }
        });

        //Set real data to activity components
        title.setText(R.string.send_title);
        text.setText(R.string.send_text);
        nextButton.setText(R.string.send_button);
    }

    private void nextActivity(){
        connectionFailed = false;
        error.setVisibility(View.INVISIBLE);
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, HRV> {
        @Override
        protected HRV doInBackground(Void... params) {
            try {

                 restAdapter.sendHRVs(hrvAdapter.getHRV());

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                connectionFailed = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(HRV hrv) {
            if (!connectionFailed) {
                Intent intent = new Intent(context, scheduler.chooseNextActivity(context));
                startActivity(intent);
            }
            else
                error.setVisibility(View.VISIBLE);

        }


    }




}
