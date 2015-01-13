package isst.fraunhofer.de.ompi.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.model.Person;


public class RegistrationActivity extends BasicActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    Scheduler scheduler;
    PersonAdapter personAdapter;
    Person person;

    Context context;
    Button nextButton;
    TextView text,title,validationError;
    EditText id1,id2,id3,id4;
    RadioGroup sexGroup;
    RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        settings = getSharedPreferences(PREFS_NAME, 0);
        context = getApplicationContext();
        scheduler=Scheduler.getInstance(this);
        personAdapter = PersonAdapter.getInstance(this);
        person = personAdapter.getPerson();

        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        id1 = (EditText) this.findViewById(R.id.regId1);
        id2 = (EditText) this.findViewById(R.id.regId2);
        id3 = (EditText) this.findViewById(R.id.regId3);
        id4 = (EditText) this.findViewById(R.id.regId4);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        validationError = (TextView) this.findViewById(R.id.validationError);
        sexGroup=(RadioGroup)this.findViewById(R.id.radioSex);

        title.setText(R.string.registration_title);
        text.setText(R.string.registration_text);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputIsValid()) {
                    validationError.setVisibility(View.INVISIBLE);
                    saveId();
                    nextActivity();
                }
                else
                    validationError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveId()
    {
        String s1=id1.getText().toString();
        String s2=id2.getText().toString();
        String s3=id3.getText().toString();
        String s4=id4.getText().toString();

        person.setLongId(s1+s2+s3+s4);

        int selectedId = sexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        person.setSex(radioSexButton.getText().toString());

        personAdapter.saveAll();

    }

    private void nextActivity(){
        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);

    }

    public boolean inputIsValid(){
        return (id1.getText().length()> 0 &&
                id2.getText().length()> 1 &&
                id3.getText().length()> 0 &&
                id4.getText().length()> 0);

    }



}
