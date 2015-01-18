package isst.fraunhofer.de.ompi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.GCMAdapter;
import isst.fraunhofer.de.ompi.adapter.HRVAdapter;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.model.Person;

public class InstallHRVActivity extends BasicActivity {

    Button nextButton;
    TextView text, title,error;

    Scheduler scheduler;
    HRVAdapter hrvAdapter;
    PersonAdapter personAdapter;
    GCMAdapter gcmAdapter;

    Person person;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        scheduler = Scheduler.getInstance(this);
        hrvAdapter = HRVAdapter.getInstance(this);
        gcmAdapter = GCMAdapter.getInstance(this);
        personAdapter = PersonAdapter.getInstance(this);
        person = personAdapter.getPerson();

        //Initialize activity components
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        text = (TextView) this.findViewById(R.id.textText);
        title = (TextView) this.findViewById(R.id.textTitle);
        error = (TextView)this.findViewById(R.id.error);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();

            }
        });

        //Set real data to activity components
        title.setText(R.string.install_title);
        text.setText(R.string.install_text);
        nextButton.setText(R.string.install_button);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent;
        if (hrvAdapter.appInstalled()) {
            person.setHrvMeasurable(true);
            intent = new Intent(this, scheduler.chooseNextActivity(this));
        } else {
            person.setHrvMeasurable(false);
            intent = new Intent(this, scheduler.chooseNextActivity(this, true));
        }
        personAdapter.saveAll();
        startActivity(intent);
    }

    private void nextActivity() {

        //Internet Verbindung wird geprüft
        if (!gcmAdapter.isOnline()){
            error.setVisibility(View.VISIBLE);
            return;
        }

        gcmAdapter.registerGCM();
        if (hrvAdapter.appInstalled()) {
            personAdapter.saveAll();
            Intent intent = new Intent(this, scheduler.chooseNextActivity(this));
            startActivity(intent);
        } else
            hrvAdapter.installHRV();
    }

}
