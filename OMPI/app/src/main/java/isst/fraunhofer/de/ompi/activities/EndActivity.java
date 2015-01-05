package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.Scheduler;

public class EndActivity extends Activity {

    Button nextButton;
    TextView text,title;
    Scheduler scheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        scheduler=Scheduler.getInstance(this);

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
        text.setText(R.string.endofexperiment_title);
        title.setText(R.string.endofexperiment_text);

    }

    private void nextActivity(){
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);
    }

}
