package com.project;

import java.io.File;
import java.io.IOException;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class select extends Activity{
	
	String filename=null;
	CountDownTimer t;
	private Camera myCamera;
    private MyCameraSurfaceView myCameraSurfaceView;
    private MediaRecorder mediaRecorder;
    Integer cnt=0;
	TextView myButton;
	SurfaceHolder surfaceHolder;
	boolean recording;
	private TextView txtcount;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        recording = false;
        
        setContentView(R.layout.select);
        
        init();
        
        myCamera = getCameraInstance();
        if(myCamera == null){
        	Toast.makeText(select.this, 
        			"Fail to get Camera", 
        			Toast.LENGTH_LONG).show();
        }

        myCameraSurfaceView = new MyCameraSurfaceView(this, myCamera);
        FrameLayout myCameraPreview = (FrameLayout)findViewById(R.id.videoview);
        myCameraPreview.addView(myCameraSurfaceView);
        
        myButton = (TextView)findViewById(R.id.mybutton);
        myButton.setOnClickListener(myButtonOnClickListener);
    }
    
    private void init() {
		// TODO Auto-generated method stub
		
    	txtcount = (TextView) findViewById(R.id.txtcounter);
    	
    	t = new CountDownTimer( Long.MAX_VALUE , 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				cnt++;
				String time = new Integer(cnt).toString();
				
				long millis = cnt;
		           int seconds = (int) (millis / 60);
		           int minutes = seconds / 60;
		           seconds     = seconds % 60;

		           txtcount.setText(String.format("%d:%02d:%02d", minutes, seconds,millis));
				//txtcount.setText(time);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
		}.start();
	}

	TextView.OnClickListener myButtonOnClickListener
    = new TextView.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			if(recording){
                // stop recording and release camera
				
			
                mediaRecorder.stop();  // stop the recording
                t.cancel();
                filename();
                releaseMediaRecorder(); // release the MediaRecorder object
                
                //Exit after saved
                finish();
			}else{
				
				//Release Camera before MediaRecorder start
				releaseCamera();
				
		        if(!prepareMediaRecorder()){
		        	Toast.makeText(select.this, 
		        			"Fail in prepareMediaRecorder()!\n - Ended -", 
		        			Toast.LENGTH_LONG).show();
		        	finish();
		        }
				
				mediaRecorder.start();
				recording = true;
				myButton.setText("STOP RECORD");
			}
		}};
    
    private Camera getCameraInstance(){
		// TODO Auto-generated method stub
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
	}
	
    private void filename()
    {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle("Save Video");
    	alert.setMessage("Enter File Name");

    	// Set an EditText view to get user input 
    	final EditText input = new EditText(this);
    	alert.setView(input);

    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    		if(input.getText().length()>=1)
    		{
    			filename = input.getText().toString();
    		}
    		else
    		{
    			filename();
    		}
    	  // Do something with value!
    	  }
    	});

    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int whichButton) {
    	    // Canceled.
    	  }
    	});

    	alert.show();
    }
	private boolean prepareMediaRecorder(){
	    myCamera = getCameraInstance();
	    mediaRecorder = new MediaRecorder();

	    myCamera.unlock();
	    mediaRecorder.setCamera(myCamera);

	    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
	    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

	    mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

	   
	    File folder = new File(Environment.getExternalStorageDirectory() + "/VideoRecord");
	    boolean success = false;
	    if (!folder.exists()) {
	        success = folder.mkdir();
	    }
	    if (!success) {
	        // Do something on success
	    } else {
	        // Do something else on failure 
	    }
	    
	    mediaRecorder.setOutputFile("/sdcard/VideoRecord/"+filename+".mp4");
        mediaRecorder.setMaxDuration(60000); // Set max duration 60 sec.
        mediaRecorder.setMaxFileSize(5000000); // Set max file size 5M

	    mediaRecorder.setPreviewDisplay(myCameraSurfaceView.getHolder().getSurface());

	    try {
	        mediaRecorder.prepare();
	    } catch (IllegalStateException e) {
	        releaseMediaRecorder();
	        return false;
	    } catch (IOException e) {
	        releaseMediaRecorder();
	        return false;
	    }
	    return true;
		
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder(){
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            myCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera(){
        if (myCamera != null){
            myCamera.release();        // release the camera for other applications
            myCamera = null;
        }
    }
	
	public class MyCameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

		private SurfaceHolder mHolder;
	    private Camera mCamera;
		
		public MyCameraSurfaceView(Context context, Camera camera) {
	        super(context);
	        mCamera = camera;

	        // Install a SurfaceHolder.Callback so we get notified when the
	        // underlying surface is created and destroyed.
	        mHolder = getHolder();
	        mHolder.addCallback(this);
	        // deprecated setting, but required on Android versions prior to 3.0
	        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	    }

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int weight,
				int height) {
	        // If your preview can change or rotate, take care of those events here.
	        // Make sure to stop the preview before resizing or reformatting it.

	        if (mHolder.getSurface() == null){
	          // preview surface does not exist
	          return;
	        }

	        // stop preview before making changes
	        try {
	            mCamera.stopPreview();
	        } catch (Exception e){
	          // ignore: tried to stop a non-existent preview
	        }

	        // make any resize, rotate or reformatting changes here

	        // start preview with new settings
	        try {
	            mCamera.setPreviewDisplay(mHolder);
	            mCamera.startPreview();

	        } catch (Exception e){
	        }
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			// The Surface has been created, now tell the camera where to draw the preview.
	        try {
	            mCamera.setPreviewDisplay(holder);
	            mCamera.startPreview();
	        } catch (IOException e) {
	        }
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
	}
}