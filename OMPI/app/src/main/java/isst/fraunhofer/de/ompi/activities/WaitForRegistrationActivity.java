package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.Scheduler;

public class WaitForRegistrationActivity extends BasicActivity {


    TextView text,title;
    Scheduler scheduler;
    ScrollView scrollView;
    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        scheduler=Scheduler.getInstance(this);
        context=this;

        //Initialize activity components
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);

       /*
        scrollView=(ScrollView)this.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new MyOnTouch());
*/
        //Set real data to activity components
        title.setText(R.string.wait_title);
        text.setText(R.string.wait_text);

    }





}
