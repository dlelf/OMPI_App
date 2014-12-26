package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
//import isst.fraunhofer.de.ompi.activities.util.SystemUiHider;


public class TemplateActivity extends Activity {

    Button nextButton;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        nextButton = (Button) this.findViewById(R.id.dummy_next_button);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { nextActivity();

            }
        });
    }

    private void nextActivity(){
        Intent intent = new Intent(this, RegistrierungActivity.class);
        startActivity(intent);

    }




}
