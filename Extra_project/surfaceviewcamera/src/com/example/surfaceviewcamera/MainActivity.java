package com.example.surfaceviewcamera;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.provider.Settings;

public class MainActivity  extends Activity implements SurfaceHolder.Callback{

	 Camera camera;
	 SurfaceView surfaceView;
	 SurfaceHolder surfaceHolder;
	 boolean previewing = false;;
	 
	 @SuppressLint("SdCardPath")
	String stringPath = Environment.getDownloadCacheDirectory() +"/samplevideo.3gp";
	 
	   /** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_main);
	      
	       Button buttonStartCameraPreview = (Button)findViewById(R.id.startcamerapreview);
	       Button buttonStopCameraPreview = (Button)findViewById(R.id.stopcamerapreview);
	      
	       getWindow().setFormat(PixelFormat.UNKNOWN);
	       surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
	       surfaceHolder = surfaceView.getHolder();
	       surfaceHolder.addCallback(this);
	       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	      
	       buttonStartCameraPreview.setOnClickListener(new Button.OnClickListener(){

	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
		   turnGPSOn();
	    if(!previewing){
	     camera = Camera.open();
	     if (camera != null){
	      try {
	    	
	       camera.setPreviewDisplay(surfaceHolder);
	       camera.startPreview();
	       previewing = true;
	      } catch (IOException e) {
	       // TODO Auto-generated catch block
	       e.printStackTrace();
	      }
	     }
	    }
	   }});
	      
	       buttonStopCameraPreview.setOnClickListener(new Button.OnClickListener(){

	   @Override
	   public void onClick(View v) {
	    // TODO Auto-generated method stub
		   turnGPSOff();
	    if(camera != null && previewing){
	    	
	     camera.stopPreview();
	     camera.release();
	     camera = null;
	     
	     previewing = false;
	    }
	   }});
	      
	   }
	  
	  

	 @Override
	 public void surfaceChanged(SurfaceHolder holder, int format, int width,
	   int height) {
	  // TODO Auto-generated method stub
	  
	 }

	 @Override
	 public void surfaceCreated(SurfaceHolder holder) {
	  // TODO Auto-generated method stub
		 
		 Toast.makeText(getApplicationContext(), "surface created", Toast.LENGTH_LONG).show();
	  
	 }

	 @Override
	 public void surfaceDestroyed(SurfaceHolder holder) {
	  // TODO Auto-generated method stub
		 Toast.makeText(getApplicationContext(), "surface destory", Toast.LENGTH_LONG).show();
	 }
	 
	 private void turnGPSOn(){
		    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		    if(!provider.contains("gps")){ //if gps is disabled
		        final Intent poke = new Intent();
		        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider"); 
		        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		        poke.setData(Uri.parse("3")); 
		        sendBroadcast(poke);
		    }
		}

		private void turnGPSOff(){
		    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		    if(provider.contains("gps")){ //if gps is enabled
		        final Intent poke = new Intent();
		        poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		        poke.setData(Uri.parse("3")); 
		        sendBroadcast(poke);
		    }
		}
	}