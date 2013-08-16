package com.VideoStopWatch;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectActivity extends Activity{
	

	
    private TextView txtrecord;
	private TextView txtplay;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        setContentView(R.layout.main);
        
        init();
        
        txtrecord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent intent  = new Intent(ProjectActivity.this,select.class);
				startActivity(intent);
			}
		});
        
        txtplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(ProjectActivity.this,filelist.class);
				startActivity(intent);
			}
		});
        
    }

	private void init() {
		// TODO Auto-generated method stub
		
		txtrecord = (TextView) findViewById(R.id.txtrecord);
		txtplay = (TextView) findViewById(R.id.txtplay);
	}
}