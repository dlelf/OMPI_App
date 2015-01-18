package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.Scheduler;

public class LinkActivity extends BasicActivity {

    Button nextButton;
    TextView text,title,link;
    Scheduler scheduler;
    ScrollView scrollView;
    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        scheduler=Scheduler.getInstance(this);
        context=this;

        //Initialize activity components
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);
        link=(TextView)this.findViewById(R.id.textLink);


        //Set real data to activity components
        title.setText(R.string.link_title);
        text.setText(R.string.link_text);
        link.setText(R.string.link_link);

    }


}
