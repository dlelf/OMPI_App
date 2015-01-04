package isst.fraunhofer.de.ompi.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.concurrent.atomic.AtomicInteger;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.adapter.GCMAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;


public class RegistrationActivity extends Activity {

    GCMAdapter gcmAdapter;


    public static final String PREFS_NAME = "MyPrefsFile";
    final String appPackageName = "com.RMT.ompihrv";
    SharedPreferences settings;
    Scheduler scheduler;

    Context context;
    Button nextButton;
    TextView text,title;
    EditText id1,id2,id3,id4;
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        settings = getSharedPreferences(PREFS_NAME, 0);
        context = getApplicationContext();
        gcmAdapter = GCMAdapter.getInstance(context);
        scheduler=Scheduler.getInstance(this);

        nextButton = (Button) this.findViewById(R.id.dummy_next_button);
        id1 = (EditText) this.findViewById(R.id.regId1);
        id2 = (EditText) this.findViewById(R.id.regId2);
        id3 = (EditText) this.findViewById(R.id.regId3);
        id4 = (EditText) this.findViewById(R.id.regId4);
        text = (TextView)this.findViewById(R.id.textText);
        title = (TextView)this.findViewById(R.id.textTitle);

        title.setText(R.string.registration_title);
        text.setText(R.string.registration_text);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gcmAdapter.registerGCM();
                saveId();
                nextActivity();
            }
        });
    }



    private void saveId()
    {

        String s1=id1.getText().toString();
        String s2=id2.getText().toString();
        String s3=id3.getText().toString();
        String s4=id4.getText().toString();

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("personId", s1+s2+s3+s4);
        editor.commit();

    }

    private void startHRV(){

        boolean installed  =  appInstalledOrNot(appPackageName);
        if(installed) {
            //This intent will help you to launch if the package is already installed
            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage(appPackageName);
            startActivityForResult(LaunchIntent,42);

        }
        else {
            try {
                startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)),42);
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)),42);
            }
        }

    }
    private void nextActivity(){

        Intent intent = new Intent(this,scheduler.chooseNextActivity(this));
        startActivity(intent);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }





}
