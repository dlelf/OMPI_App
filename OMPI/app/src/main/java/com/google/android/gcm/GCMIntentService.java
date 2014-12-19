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
import isst.fraunhofer.de.ompi.activities.TemplateActivity;


public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(getSenderId());

	}

	private static final String SENDER_ID = "215905765243";
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;

	@Override
	protected void onRegistered(final Context pContext, final String registrationId) {
		// RegistrationID an den eigenen Server senden.
		Log.i(TAG, "Device registered: regId = " + registrationId);
		String token = PreferenceManager.getDefaultSharedPreferences(pContext).getString("token", "");
		String patientid = PreferenceManager.getDefaultSharedPreferences(pContext).getString("patientid", "");

		if (token.isEmpty() || patientid.isEmpty())
			return;



	}

	@Override
	protected void onUnregistered(final Context arg0, final String arg1) {
		Log.i(TAG, "unregistered = " + arg1);
	}

	@Override
	protected void onMessage(final Context context, final Intent arg1) {

		Log.i(TAG, "onMessage, Nach GCM: ");
//		Log.i(TAG, "onMessage, Parameter: ");
		ArrayList<String> args = new ArrayList<>(arg1.getExtras().keySet());
		for (int i = 0; i < arg1.getExtras().size(); i++) {
			Log.i(TAG, "onmessage loop: "+args.get(i) + ": " + arg1.getExtras().get(args.get(i)));
		}



       saveGroupId(arg1.getStringExtra("groupId"));


                Intent serviceIntent = new Intent(context, TemplateActivity.class);
                serviceIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentTitle("adipo").setContentIntent(pendingIntent).setContentText("Sie wurden zu einer Gruppe zugeteilt")
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
    public void saveGroupId(String groupId){
        settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("groupId", groupId);
        editor.commit();

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
