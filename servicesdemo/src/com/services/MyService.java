package com.services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private static final String TAG = "MyService";
	MediaPlayer player;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		
		player = MediaPlayer.create(this, R.raw.song);
		player.setLooping(false); // Set looping
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
		player.stop();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		player.start();
		
		final int NOTIF_ID = 1234;
		NotificationManager notifManager;
		notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification note = new Notification(R.drawable.icon, "Services", System.currentTimeMillis());

        PendingIntent intent1 = PendingIntent.getActivity(this, 0, new Intent(this, MyService.class), 0);

        note.setLatestEventInfo(this, "Services Start", " .. ", intent1);
        
        notifManager.notify(NOTIF_ID, note);
	}
}
