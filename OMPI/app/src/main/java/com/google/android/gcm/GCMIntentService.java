package com.google.android.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import isst.fraunhofer.de.ompi.R;
import isst.fraunhofer.de.ompi.activities.StartActivity;
import isst.fraunhofer.de.ompi.adapter.PersonAdapter;
import isst.fraunhofer.de.ompi.adapter.Scheduler;
import isst.fraunhofer.de.ompi.adapter.StateAdapter;


public class GCMIntentService extends GCMBaseIntentService {

    PersonAdapter personAdapter=PersonAdapter.getInstance(this);
    StateAdapter stateAdapter=StateAdapter.getInstance(this);
    Scheduler scheduler=Scheduler.getInstance(this);
    String messageText;

    public GCMIntentService() {
		super(getSenderId());

	}



	private static final String SENDER_ID = "329654643597";
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;

	@Override
	protected void onRegistered(final Context pContext, final String registrationId) {
		// RegistrationID an den eigenen Server senden.
		Log.i(TAG, "Device registered: regId = " + registrationId);
		String token = PreferenceManager.getDefaultSharedPreferences(pContext).getString("token", "");




	}

	@Override
	protected void onUnregistered(final Context arg0, final String arg1) {
		Log.i(TAG, "unregistered = " + arg1);
	}

	@Override
	protected void onMessage(final Context context, final Intent arg1) {

		Log.i(TAG, "onMessage, Nach GCM: ");

		ArrayList<String> args = new ArrayList<>(arg1.getExtras().keySet());
		/*for (int i = 0; i < arg1.getExtras().size(); i++) {
			Log.i(TAG, "onmessage loop: "+args.get(i) + ": " + arg1.getExtras().get(args.get(i)));
		}*/



        switch (arg1.getStringExtra("functionName")) {
            case "setGroupId": {
                setNextDay(1);
                saveGroupId(Integer.parseInt(arg1.getStringExtra("functionValue")));
                Intent intent = new Intent(this, scheduler.onGroupIdRecieve());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case "setNextDay": {
                if (!stateAdapter.isLastDayPassed()) {
                    setNextDay(Integer.parseInt(arg1.getStringExtra("functionValue")));
                    Intent intent = new Intent(this, scheduler.onNextDayRecieve());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            }
        }

            messageText = arg1.getStringExtra("messageText");



                Intent serviceIntent = new Intent(this, StartActivity.class);
                serviceIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentTitle("ompi").setContentIntent(pendingIntent).setContentText( messageText)
                        .setSmallIcon(R.drawable.ompi_launcher);

                Notification noti = builder.build();
                noti.flags |= Notification.FLAG_AUTO_CANCEL;
                noti.defaults |= Notification.DEFAULT_VIBRATE;
                noti.defaults |= Notification.DEFAULT_LIGHTS;

                // Notification anzeigen
                NotificationManager myNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                myNotificationManager.notify(0, noti);
                PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("activated", true).commit();





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

	@Override
	protected void onError(final Context arg0, final String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(final Context context, final String errorId) {
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	public static String getSenderId() {
		return SENDER_ID;
	}

}
