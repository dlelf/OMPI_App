package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.Scheduler;

public class WaitForRegistrationActivity extends Activity {


    TextView text,title;
    Scheduler scheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        scheduler=Scheduler.getInstance(this);

        //Initialize activity components
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);

        //Set real data to activity components
        title.setText(R.string.wait_title);
        text.setText(R.string.wait_text);

    }



}
