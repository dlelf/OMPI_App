package isst.fraunhofer.de.ompi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;

public class InstructionActivity extends BasicActivity {

    Button nextButton;
    TextView text,title,taskTitle,taskText;
    Scheduler scheduler;
    PersonAdapter personAdapter;
    StateAdapter stateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        scheduler = Scheduler.getInstance(this);
        personAdapter = PersonAdapter.getInstance(this);
        stateAdapter=StateAdapter.getInstance(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        taskText = (TextView) this.findViewById(R.id.taskText);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();

            }
        });

        //Set real data to activity components
        switch (personAdapter.getPerson().getGroupNr())
        {
        case 1:{taskText.setText(R.string.task1_text);break;}
        case 2:{taskText.setText(R.string.task2_text);break;}
        case 3:{taskText.setText(R.string.task3_text);break;}
        }

    }

    private void nextActivity(){
        Intent intent;
        switch (personAdapter.getPerson().getGroupNr()){
            case 1: {intent = new Intent(this,scheduler.chooseNextActivity(this));break;}
            case 2: {intent = new Intent(this,scheduler.chooseNextActivity(this,true));break;}
            case 3: {intent = new Intent(this,scheduler.chooseNextActivity(this));break;}
            default: {intent = new Intent(this,scheduler.chooseNextActivity(this));break;}
        }
        //intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);
    }

}
