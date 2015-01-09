package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.HRVAdapter;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;
import isst.fraunhofer.de.ompi.model.HRV;

public class HRVResultActivity extends Activity {

    Button nextButton;
    TextView text, title,error;
    Scheduler scheduler;
    HRVAdapter hrvAdapter;
    StateAdapter stateAdapter;
    RestAdapter restAdapter;
    boolean hrvValid,connectionFailed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddata);
        scheduler = Scheduler.getInstance(this);
        hrvAdapter = HRVAdapter.getInstance(this);
        stateAdapter = StateAdapter.getInstance(this);
        restAdapter=RestAdapter.getInstance(this);

        //Check, if HRV richtig gelesen wurde
        hrvValid = hrvAdapter.isHRVValid(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView) this.findViewById(R.id.textText);
        title = (TextView) this.findViewById(R.id.textTitle);
        error = (TextView)this.findViewById(R.id.error);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionFailed = false;
                error.setVisibility(View.INVISIBLE);
                new HttpRequestTask().execute();

            }
        });


        //Set real data to activity components
        if (hrvValid) {
            title.setText(R.string.hrvResultOk_title);
            text.setText(R.string.hrvResultOk_text);
        } else {
            title.setText(R.string.hrvResultFailed_title);
            text.setText(R.string.hrvResultFailed_text);
        }
        nextButton.setText(R.string.hrvResult_button);

    }


    private class HttpRequestTask extends AsyncTask<Void, Void, HRV> {
        @Override
        protected HRV doInBackground(Void... params) {
            try {
                ArrayList hrv = hrvAdapter.getHRV();
                restAdapter.sendHRVs(hrv);
                hrvAdapter.deleteHrvFile();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                connectionFailed = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(HRV hrv) {
            if (!connectionFailed) {
                nextActivity();
            } else
                error.setVisibility(View.VISIBLE);

        }
    }


    private void nextActivity() {
        Intent intent;
        if (hrvValid) {
            if (stateAdapter.getState().isFirstHrv())
                intent = new Intent(this, scheduler.chooseNextActivity(this));
            else
                intent = new Intent(this, scheduler.chooseNextActivity(this, true));
            stateAdapter.inverseFirstHRV();
            startActivity(intent);
        } else {
            //TODO Start OMPI-HRV wieder
        }
    }
}
