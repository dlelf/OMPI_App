package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.HRVAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;

public class HRVResultActivity extends Activity {

    Button nextButton;
    TextView text,title;
    Scheduler scheduler;
    HRVAdapter hrvAdapter;
    boolean hrvValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        scheduler=Scheduler.getInstance(this);
        hrvAdapter=HRVAdapter.getInstance(this);

        //Check, if HRV richtig gelesen wurde
        hrvValid=hrvAdapter.validateHRV(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { nextActivity();

            }
        });


        //Set real data to activity components
        if (hrvValid) {
            title.setText(R.string.hrvResultOk_title);
            text.setText(R.string.hrvResultOk_text);
        }
        else{
            title.setText(R.string.hrvResultFailed_title);
            text.setText(R.string.hrvResultFailed_text);
        }
        nextButton.setText(R.string.hrvResult_button);

    }

    private void nextActivity(){
        Intent intent;
        if (hrvValid){
        if (scheduler.getActivityBefore().getClass().equals(TaskActivity.class))
        intent = new Intent(this,scheduler.chooseNextActivity(this));
            else
        intent = new Intent(this,scheduler.chooseNextActivity(this,true));
        startActivity(intent);}
        else {
            //TODO Start OMPI-HRV wieder
        }
    }





}
