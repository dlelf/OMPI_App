package isst.fraunhofer.de.ompi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;


public class StartActivity extends BasicActivity {

    Button nextButton;
    TextView text;
    Scheduler scheduler;
    StateAdapter stateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduler=Scheduler.getInstance(this);
        stateAdapter=StateAdapter.getInstance(this);


        //scheduler.setStartActivity(HRVCheckActivity.class);
        //stateAdapter.nextDay();



        Intent intent = new Intent(this,scheduler.getLastActivity());
        startActivity(intent);
        //nextActivity();

    }

    private void nextActivity(){
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);

    }
}
