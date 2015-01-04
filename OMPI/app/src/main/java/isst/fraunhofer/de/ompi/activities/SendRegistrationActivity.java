package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.model.Person;


public class SendRegistrationActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
   PersonAdapter personAdapter;
    RestAdapter restAdapter;
    Scheduler scheduler;
    Button nextButton;
    TextView text,title;
    Person person;
    Context context;


    @Override
    protected void onStart() {
        super.onStart();
        personAdapter = PersonAdapter.getInstance(this);
        restAdapter = RestAdapter.getInstance(this);
        scheduler= Scheduler.getInstance(this);
        person=personAdapter.getPerson();
        //new HttpRequestTask().execute();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);


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
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Person> {
        @Override
        protected Person doInBackground(Void... params) {
            try {

                 restAdapter.sendPerson(person);

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return person;
        }

        @Override
        protected void onPostExecute(Person person) {
            text.setText(person.getLongId());
        }
    }




}
