package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;

public class WaitForNextDayActivity extends BasicActivity {


    TextView text,title;
    Scheduler scheduler;
    Activity context;
    ScrollView scrollView;
    StateAdapter stateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Initialize Activity
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_wait);
        scheduler=Scheduler.getInstance(this);
        stateAdapter=StateAdapter.getInstance(this);
        context=this;

        if(stateAdapter.isLastDay()){
            Intent intent = new Intent(this,scheduler.getLastActivity());
            startActivity(intent);
        }

        //Initialize activity components
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);

        //TODO Delete after debug
     /*   scrollView=(ScrollView)this.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new MyOnTouch());*/

        //Set real data to activity components
        title.setText(R.string.endofday_title);
        text.setText(R.string.endofday_text);

    }

   /* //TODO Delete after debug
    public class MyOnTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return pageFlip(v, event);
        }

        public boolean pageFlip(View v, MotionEvent event) {
            Intent intent = new Intent(context,scheduler.chooseNextActivity(context,true));
            startActivity(intent);
            return true;
        }*/


}
