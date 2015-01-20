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
import isst.fraunhofer.de.ompi.model.Person;


public class SendRegistrationActivity extends BasicActivity {


   PersonAdapter personAdapter;
    RestAdapter restAdapter;
    Scheduler scheduler;
    HRVAdapter hrvAdapter;
    Button nextButton;
    TextView text,title,error,sending;
    Person person;
    Activity context;
    boolean connectionFailed;


    @Override
    protected void onStart() {
        super.onStart();
        personAdapter = PersonAdapter.getInstance(this);
        restAdapter = RestAdapter.getInstance(this);
        hrvAdapter=HRVAdapter.getInstance(this);
        scheduler= Scheduler.getInstance(this);
        person=personAdapter.getPerson();
        context=this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddata);
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        error = (TextView)this.findViewById(R.id.error);
        sending = (TextView) this.findViewById(R.id.sending);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButton.setEnabled(false);
                sending.setVisibility(View.VISIBLE);
                updatePerson();
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

    private class HttpRequestTask extends AsyncTask<Void, Void, Person> {
        @Override
        protected Person doInBackground(Void... params) {
            try {
                 restAdapter.sendPerson(person);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                connectionFailed = true;
            }
            return person;
        }

        @Override
        protected void onPostExecute(Person person) {
            if (!connectionFailed) {
                Intent intent = new Intent(context, scheduler.chooseNextActivity(context));
                hrvAdapter.deleteHrvFile();
                startActivity(intent);
            }
            else
                error.setVisibility(View.VISIBLE);
            nextButton.setEnabled(true);

        }
    }

    public void updatePerson(){
        boolean hrvValid=(hrvAdapter.getHRV()!=null);
        personAdapter.getPerson().setHrvMeasurable(hrvValid);

    }




}
