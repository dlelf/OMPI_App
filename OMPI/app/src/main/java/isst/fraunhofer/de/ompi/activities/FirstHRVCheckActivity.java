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

public class FirstHRVCheckActivity extends Activity {

    Button nextButton;
    TextView text,title;
    Scheduler scheduler;
    HRVAdapter hrvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        scheduler=Scheduler.getInstance(this);
        hrvAdapter= HRVAdapter.getInstance(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  hrvAdapter.startHRV();
                nextActivity();

            }
        });

        //Set real data to activity components
        title.setText(R.string.hrvcheck_title);
        text.setText(R.string.hrvcheck_text);
        nextButton.setText(R.string.hrvcheck_button);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        nextActivity();
    }

    private void nextActivity(){
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);
    }

}
