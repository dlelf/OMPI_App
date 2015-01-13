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
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;
import isst.fraunhofer.de.ompi.model.HRV;

public class HRVResultActivity extends BasicActivity {

    Button nextButton;
    TextView text, title, error,sending;
    Scheduler scheduler;
    HRVAdapter hrvAdapter;
    StateAdapter stateAdapter;
    PersonAdapter personAdapter;
    RestAdapter restAdapter;
    boolean hrvValid, connectionFailed;
    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddata);
        scheduler = Scheduler.getInstance(this);
        hrvAdapter = HRVAdapter.getInstance(this);
        stateAdapter = StateAdapter.getInstance(this);
        restAdapter = RestAdapter.getInstance(this);
        personAdapter=PersonAdapter.getInstance(this);
        context = this;

        //Check, if HRV richtig gelesen wurde
        hrvValid = hrvAdapter.isHRVValid(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView) this.findViewById(R.id.textText);
        title = (TextView) this.findViewById(R.id.textTitle);
        error = (TextView) this.findViewById(R.id.error);
        sending = (TextView) this.findViewById(R.id.sending);

        //Set real data to activity components
        if (hrvValid) {
            title.setText(R.string.hrvResultOk_title);
            text.setText(R.string.hrvResultOk_text);
        } else {
            title.setText(R.string.hrvResultFailed_title);
            text.setText(R.string.hrvResultFailed_text);
        }

        nextButton.setText(R.string.hrvResult_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.setEnabled(false);
                hrvValid = hrvAdapter.isHRVValid(context);
                if (!hrvValid) backToHrv();
                else sendData();

            }
        });


    }

    public void backToHrv() {
        Intent intent = new Intent(this, scheduler.chooseBeforeActivity(this));
        startActivity(intent);
    }

    public void sendData() {
        connectionFailed = false;
        error.setVisibility(View.INVISIBLE);
        sending.setVisibility(View.VISIBLE);
        new HttpRequestTask().execute();
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, HRV> {
        @Override
        protected HRV doInBackground(Void... params) {
            connectionFailed = false;
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
            } else{
                error.setVisibility(View.VISIBLE);
                sending.setVisibility(View.VISIBLE);}
            nextButton.setEnabled(true);

        }
    }


    private void nextActivity() {
        Intent intent;
        if (stateAdapter.getState().isFirstHrv())
            if (personAdapter.getPerson().getGroupNr()==2)
                intent = new Intent(this, scheduler.setNextActivity(PlaceboTaskActivity.class));
            else
                intent = new Intent(this, scheduler.chooseNextActivity(this));
        else
            intent = new Intent(this, scheduler.chooseNextActivity(this, true));
        stateAdapter.inverseFirstHRV();
        startActivity(intent);
    }
}
