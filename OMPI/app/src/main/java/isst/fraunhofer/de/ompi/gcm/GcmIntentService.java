package isst.fraunhofer.de.ompi.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.activities.StartActivity;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;

/**
 * Created by durin on 16/1/2015.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    public static final String TAG = "GCM";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    PersonAdapter personAdapter;
    StateAdapter stateAdapter;
    Scheduler scheduler;
    String messageText;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        personAdapter=PersonAdapter.getInstance(this);
        stateAdapter=StateAdapter.getInstance(this);
        scheduler=Scheduler.getInstance(this);



        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            switch (extras.getString("functionName")) {
                case "setGroupId": {
                   if (personAdapter.getPerson().getGroupNr()==0) {
                        setNextDay(1);
                        saveGroupId(Integer.parseInt(extras.getString("functionValue")));
                        Intent newIntent = new Intent(this, scheduler.onGroupIdReceive());
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                    }
                    break;
                }
                case "setNextDay": {
                    if (!stateAdapter.isLastDay()) {
                        setNextDay(Integer.parseInt(extras.getString("functionValue")));
                        Intent newIntent = new Intent(this, scheduler.onNextDayReceive());
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                    }
                    break;
                }
                case "setActivity": {
                    if (!stateAdapter.isLastDay()) {
                        Intent newIntent = new Intent(this, scheduler.onActivityReceive(extras.getString("functionValue")));
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(newIntent);
                    }
                    break;
                }
                case "message": {

                    break;
                }
            }

            messageText = extras.getString("messageText");




                // Post notification of received message.
                sendNotification("Received: " + messageText);
                Log.i(TAG, "Received: " + messageText);

        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, StartActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ompi_launcher)
                        .setContentTitle("OMPI Nachricht")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public void saveGroupId(int groupId){
        personAdapter.setGroupId(groupId);

    }

    public  void setNextDay(int dayNr){
        if (dayNr==0)
            stateAdapter.nextDay();
        else
            stateAdapter.setDayNr(dayNr);
    }

    public void navigateTo(int ordinal){

    }

}
