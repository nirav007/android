package com.example.videorecord1;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ZoomControls;


public class video extends Activity   {
	

	private static final String TAG = "Touch";
	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	Matrix savedMatrix2 = new Matrix();
	float mx,my;
	private static final int WIDTH = 0;
	 private static final int HEIGHT = 1;
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	
	
	private VideoView videoview;
	private String getname;
	TextView xView;

	
		
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.video);
		init();
		
		ImageView imageView = new ImageView(this);
		
		 
	        MediaController mediaController = new MediaController(this);
	        mediaController.setAnchorView(videoview);
	        videoview.setMediaController(mediaController);
	        
	        videoview.setVideoPath(getname);
	        
	    
	        videoview.start();
	      
	        Timer timer = new Timer(); 
	        videoview.setOnTouchListener(new OnTouchListener() {
	        	
				
				public boolean onTouch(View v, MotionEvent event) {
					
			
					return false;
				}
			});
	     
	       
	      
		
	}


	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		 if ((keyCode == KeyEvent.KEYCODE_BACK)) {

			 	Intent intent = new Intent(video.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 	startActivity(intent);
		 }
		return super.onKeyDown(keyCode, event);
	}


	private void init() {
		
		videoview = (VideoView) findViewById(R.id.videoview);
		
		Bundle bb = getIntent().getExtras();
		getname = bb.getString("fname");
	}



		    public static boolean SmallerThanIdentity(android.graphics.Matrix m) {
		        float[] values = new float[9];
		        m.getValues(values);
		        return ((values[0] < 1.0) || (values[4] < 1.0) || (values[8] < 1.0));
		    }

		    public void resetView(View v) {
		        ImageView view = (ImageView) v;
		        matrix = new Matrix();

		        view.setScaleType(ImageView.ScaleType.MATRIX);
		        view.setImageMatrix(matrix);
		    }

		
}
