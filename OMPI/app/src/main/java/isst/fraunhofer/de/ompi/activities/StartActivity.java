package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.Scheduler;


public class StartActivity extends Activity {

    Button nextButton;
    TextView text;
    Scheduler scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduler=Scheduler.getInstance(this);

       // scheduler.setStartActivity(new RegistrierungActivity());
        scheduler.setStartActivity(RegistrationActivity.class);

        Intent intent = new Intent(this,scheduler.getLastActivity());
        startActivity(intent);
        //nextActivity();

    }

    private void nextActivity(){
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);

    }
}
