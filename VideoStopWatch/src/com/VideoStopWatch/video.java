package com.VideoStopWatch;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ZoomControls;


public class video extends Activity  {
	
	private VideoView videoview;
	private String getname;
	TextView xView;

	@Override
		
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.video);
		init();
		
		ImageView imageView = new ImageView(this);
		
		 
	        MediaController mediaController = new MediaController(this);
	        mediaController.setAnchorView(videoview);
	        videoview.setMediaController(mediaController);
	        
	        videoview.setVideoPath("/sdcard/VideoRecord/"+getname);

	        videoview.start();
	        ZoomControls zoomControls = (ZoomControls) findViewById(R.id.zoomcontrols);      zoomControls.setOnZoomInClickListener(new View.OnClickListener() {              
	        	 @Override                  
	        	public void onClick(View v) {                         
	        	 //mapController.zoomIn();
	        	 Toast.makeText(getApplicationContext(), "zoomin", Toast.LENGTH_LONG).show();
	        	  }        
	        	 });        
	        	 zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {               
	        	@Override               
	        	  public void onClick(View v) {                        
	        	 //mapController.zoomOut(); 
	        		
	        	 Toast.makeText(getApplicationContext(), "zoomout", Toast.LENGTH_LONG).show();
	        	 }       
	        	  });  
	        
	      /*  xView = (TextView)findViewById( R.id.aa);
	        
	        final ZoomButtonsController controller = new ZoomButtonsController(xView);
	        ViewGroup zoomControlContainer = controller.getContainer();
	        FrameLayout layout = (FrameLayout) findViewById(R.id.v);
	        layout.addView(zoomControlContainer);
	        controller.setAutoDismissed(true);
	        controller.getZoomControls();
	        controller.setOnZoomListener(new OnZoomListener() {
			
			@Override
			public void onZoom(boolean zoomIn) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "sad", Toast.LENGTH_LONG).show();
				
				
			}
			
			
			@Override
			public void onVisibilityChanged(boolean visible) {
				// TODO Auto-generated method stub
				
			}
		});*/
	        
	        videoview.setOnTouchListener(new OnTouchListener() {
	        	
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
				//	Toast.makeText(getApplicationContext(), "DSA", Toast.LENGTH_LONG).show();
					return false;
				}
			});
	     
	       
	        Timer timer = new Timer(); 
	       
	        timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
						
					videoview.start();
					
				}
			}, 20, 200);
	        timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					videoview.pause();
					
				}
			}, 20, 200);
		
		
	}

	 
	


	private void init() {
		
		videoview = (VideoView) findViewById(R.id.videoview);
		
		Bundle bb = getIntent().getExtras();
		getname = bb.getString("fname");
	}

}
