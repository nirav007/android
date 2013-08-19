package com.example.notificationbuilder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity  extends Activity {
	 
	 private static final int MY_NOTIFICATION_ID=1;
	 NotificationManager notificationManager;
	 Notification myNotification;
	 private final String myBlog = "http://android-er.blogspot.com/";

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	  Button buttonSend = (Button)findViewById(R.id.send);
	  buttonSend.setOnClickListener(new OnClickListener(){

	   @Override
	   public void onClick(View arg0) {
	    Context context = getApplicationContext();
	    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
	    PendingIntent pendingIntent = PendingIntent.getActivity(
	      MainActivity.this, 
	      0, 
	      myIntent, 
	      Intent.FLAG_ACTIVITY_NEW_TASK);
	      
	    myNotification =  new Notification.Builder(getApplicationContext())
	          .setContentTitle("Exercise of Notification!")
	          .setContentText("http://android-er.blogspot.com/")
	          .setTicker("Notification!")
	          .setWhen(System.currentTimeMillis())
	          .setContentIntent(pendingIntent)
	          .setDefaults(Notification.DEFAULT_SOUND)
	          .setAutoCancel(true)
	          .setSmallIcon(R.drawable.ic_launcher)
	          .build();
	    
	    notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.notify(MY_NOTIFICATION_ID, myNotification);

	   }});

	 }

	}