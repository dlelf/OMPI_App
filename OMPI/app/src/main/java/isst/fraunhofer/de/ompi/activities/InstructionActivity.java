package isst.fraunhofer.de.ompi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;

public class InstructionActivity extends BasicActivity {

    Button nextButton;
    TextView text,title,taskTitle,taskText;
    Scheduler scheduler;
    PersonAdapter personAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        scheduler = Scheduler.getInstance(this);
        personAdapter = PersonAdapter.getInstance(this);

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView) this.findViewById(R.id.textText);
        title = (TextView) this.findViewById(R.id.textTitle);
        taskText = (TextView) this.findViewById(R.id.taskText);
        taskTitle = (TextView) this.findViewById(R.id.taskTitle);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();

            }
        });

        //Set real data to activity components
        title.setText(R.string.instruction_title);
        text.setText(R.string.instruction_text);
        taskTitle.setText(R.string.task_title);
        switch (personAdapter.getPerson().getGroupNr())
        {
        case 1:taskText.setText(R.string.task1_text);
        case 2:taskText.setText(R.string.task2_text);
        case 3:taskText.setText(R.string.task3_text);
        }
        //TODO check groupNr and show suitable taskText
        int groupNr = personAdapter.getPerson().getGroupNr();
        switch (groupNr) {
            case 1: {
                text.setText(R.string.task1_text);
                break;
            }
            case 2: {
                text.setText(R.string.task2_text);
                break;
            }
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
