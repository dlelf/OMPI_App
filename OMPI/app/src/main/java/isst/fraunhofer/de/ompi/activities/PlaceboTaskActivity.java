package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.RestAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.TaskAdapter;
import isst.fraunhofer.de.ompi.model.EarlyMemory;
import isst.fraunhofer.de.ompi.model.Person;

public class PlaceboTaskActivity extends Activity {

    Button nextButton;
    TextView text,title,error;
    EditText memory;

    Scheduler scheduler;
    RestAdapter restAdapter;
    TaskAdapter taskAdapter;

    Activity context;
    Boolean connectionFailed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placebotask);
        scheduler=Scheduler.getInstance(this);
        taskAdapter=TaskAdapter.getInstance(this);
        restAdapter=RestAdapter.getInstance(this);
        context=this;

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        error = (TextView)this.findViewById(R.id.error);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { save(); sendData();

            }
        });
        memory = (EditText)this.findViewById(R.id.editText);

        //Set real data to activity components
        title.setText(R.string.placeboTask_title);
        text.setText(R.string.placeboTask_text);
        nextButton.setText(R.string.Task_button);


    }

    private void save(){
        taskAdapter.addEarlyMemory(memory.getText().toString());

    }

    private void sendData(){
        connectionFailed = false;
        error.setVisibility(View.INVISIBLE);
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, EarlyMemory> {
        @Override
        protected EarlyMemory doInBackground(Void... params) {
            try {
                restAdapter.sendEarlyMemory(TaskAdapter.getEarlyMemory());
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                connectionFailed = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(EarlyMemory earlyMemory) {
            if (!connectionFailed) {
                nextActivity();
            }
            else
                error.setVisibility(View.VISIBLE);

        }
    }

    private void nextActivity(){
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);
    }







}
