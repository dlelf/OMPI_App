package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import model.Person;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see isst.fraunhofer.de.ompi.activities.util.SystemUiHider
 */
public class SendRegistrationActivity extends Activity {

    public static final String PREFS_NAME = "MyPrefsFile";
   // public static final String PROPERTY_REG_ID = "registration_id";
   PersonAdapter personAdapter;

    Button nextButton;
    TextView text;
    Person person;
    Context context;


    @Override
    protected void onStart() {
        super.onStart();
        personAdapter = PersonAdapter.getInstance(this);
        person=personAdapter.getPerson();
        new HttpRequestTask().execute();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_registration);
        nextButton = (Button) this.findViewById(R.id.send_registration_next_button);
        text =(TextView) this.findViewById(R.id.send_registration_content);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();

            }
        });
    }

    private void nextActivity(){
        /*Intent intent = new Intent(this, RegistrierungActivity.class);
        startActivity(intent);*/

    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Person> {
        @Override
        protected Person doInBackground(Void... params) {
            try {
                //final String url = "http://rest-service.guides.spring.io/greeting";
                final String url = "http://192.168.200.168:8080/people";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.postForObject(url, person, Person.class);
                return person;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Person person) {
            text.setText(person.getLongId());
        }
    }




}
