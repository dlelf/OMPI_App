package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.Constants;
import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;
import isst.fraunhofer.de.ompi.adapter.TaskAdapter;
import isst.fraunhofer.de.ompi.model.GoodThing;

public class TaskActivity extends BasicActivity {

    Button nextButton;
    TextView text,title,error,validationError,dayNr;
    EditText goodThing1,goodThing2,goodThing3;
    EditText causality1,causality2,causality3;
    Boolean connectionFailed;

    Scheduler scheduler;
    RestAdapter restAdapter;
    TaskAdapter taskAdapter;
    PersonAdapter personAdapter;
    StateAdapter stateAdapter;

    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        scheduler=Scheduler.getInstance(this);
        taskAdapter=TaskAdapter.getInstance(this);
        restAdapter=RestAdapter.getInstance(this);
        personAdapter=PersonAdapter.getInstance(this);
        stateAdapter=StateAdapter.getInstance(this);
        context=this;

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        dayNr=(TextView)this.findViewById(R.id.dayNr);

        dayNr.setText(String.valueOf(stateAdapter.getState().getDayNr()));

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputIsValid())
                {   validationError.setVisibility(View.INVISIBLE);
                    nextButton.setEnabled(false);
                    save();
                    sendData();
                }
                else
                    validationError.setVisibility(View.VISIBLE);
            }
        });
        error = (TextView)this.findViewById(R.id.error);
        validationError = (TextView) this.findViewById(R.id.validationError);

        goodThing1 = (EditText)this.findViewById(R.id.goodThing1);
        goodThing2 = (EditText)this.findViewById(R.id.goodThing2);
        goodThing3 = (EditText)this.findViewById(R.id.goodThing3);

        causality1 = (EditText)this.findViewById(R.id.causality1);
        causality2 = (EditText)this.findViewById(R.id.causality2);
        causality3 = (EditText)this.findViewById(R.id.causality3);


    }

    public boolean inputIsValid(){
        return (goodThing1.getText().length()> Constants.VALID_TEXT_LENGTH &&
                goodThing2.getText().length()> Constants.VALID_TEXT_LENGTH &&
                goodThing3.getText().length()> Constants.VALID_TEXT_LENGTH &&
                causality1.getText().length()> Constants.VALID_TEXT_LENGTH &&
                causality2.getText().length()> Constants.VALID_TEXT_LENGTH &&
                causality3.getText().length()> Constants.VALID_TEXT_LENGTH);

    }

    private void save(){
        taskAdapter.addGoodThing(goodThing1.getText().toString(),causality1.getText().toString());
        taskAdapter.addGoodThing(goodThing2.getText().toString(),causality2.getText().toString());
        taskAdapter.addGoodThing(goodThing3.getText().toString(),causality3.getText().toString());

    }

    private void sendData(){
        connectionFailed = false;
        error.setVisibility(View.INVISIBLE);
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, GoodThing> {
        @Override
        protected GoodThing doInBackground(Void... params) {
            try {
                restAdapter.sendGoodThings(TaskAdapter.getGoodThings());
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                connectionFailed = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(GoodThing goodThing) {
            if (!connectionFailed) {
                nextActivity();
            }
            else
                error.setVisibility(View.VISIBLE);
            nextButton.setEnabled(true);

        }
    }

    private void nextActivity(){
        Intent intent;
        switch (personAdapter.getPerson().getGroupNr()){
            case 1: {intent = new Intent(this,scheduler.chooseNextActivity(this));break;}
            case 2: {intent = new Intent(this,scheduler.chooseNextActivity(this));break;}
            case 3: {intent = new Intent(this,scheduler.chooseNextActivity(this,true));break;}
            default: {intent = new Intent(this,scheduler.chooseNextActivity(this));break;}
        }
        //intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);
    }

}
