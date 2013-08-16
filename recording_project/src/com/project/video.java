package com.project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class video extends Activity {
	
	private VideoView videoview;
	private String getname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.video);
		init();
		 
	        MediaController mediaController = new MediaController(this);
	        mediaController.setAnchorView(videoview);
	        videoview.setMediaController(mediaController);

	        videoview.setVideoPath("/sdcard/VideoRecord/"+getname);

	        videoview.start();
	        
		
		
	}

	private void init() {
		// TODO Auto-generated method stub
		
		videoview = (VideoView) findViewById(R.id.videoview);
		
		Bundle bb = getIntent().getExtras();
		getname = bb.getString("fname");
	}

}
