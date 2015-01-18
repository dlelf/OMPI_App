package isst.fraunhofer.de.ompi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.HRVAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;

public class HRVCheckActivity extends BasicActivity {

    Button nextButton;
    TextView text,title;
    Scheduler scheduler;
    HRVAdapter hrvAdapter;
    StateAdapter stateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        scheduler=Scheduler.getInstance(this);
        hrvAdapter= HRVAdapter.getInstance(this);
        stateAdapter=StateAdapter.getInstance(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hrvAdapter.startHRV();

            }
        });

        //Set real data to activity components
        title.setText(R.string.hrvcheck_title);
        //if(stateAdapter.getState().isFirstHrv())
        text.setText(R.string.hrvcheck_text);
        //else
        //    text.setText(R.string.measure_two);
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
