package com.example.videorecord1;


import java.util.Timer;
import java.util.TimerTask;




import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.FrameLayout.LayoutParams;

public class onev extends Activity {
	private static final String TAG = "VideoViewDemo";

	private int seekForwardTime = 5000; // 5000 milliseconds
	private int seekBackwardTime = 5000; // 5000 milliseconds
	
	Integer flag=0;
	
	private VideoView mVideoView;
	private TextView mPath;
	private ImageView mPlay;
	private ImageView mPause;
	private ImageView mReset;
	private ImageView mStop,mbackword,mforward,mfullscreen;
	private String current;
	LinearLayout mtool;
	CountDownTimer t;
	Bundle bb ;
	String getname;

	Timer timer = new Timer(); 

	private TextView txtslow;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.onev);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		
		mtool = (LinearLayout) findViewById(R.id.mtool);
		bb = getIntent().getExtras();
		   

		getname = bb.getString("fname");
		
		//Toast.makeText(getApplicationContext(), getname, Toast.LENGTH_LONG).show();
		
		mPause = (ImageView) findViewById(R.id.pause);
		mReset = (ImageView) findViewById(R.id.reset);
		
		
		mfullscreen = (ImageView) findViewById(R.id.fulls);
		
		Display display = getWindowManager().getDefaultDisplay(); 
	       int width = display.getWidth();
	       int height = display.getHeight();
	       
	       String swidth = Integer.toString(width);
	       String sheight = Integer.toString(height);
	       
	     //  Toast.makeText(getApplicationContext(),"Width : "+  swidth +"Height : " + height, Toast.LENGTH_LONG).show();
	     //  mVideoView.setLayoutParams(new FrameLayout.LayoutParams(height,width));
	       
		
	    //   mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(height,width+mtool.getHeight()));
		Log.e("e", "error");
		
		txtslow =  (TextView) findViewById(R.id.txtslow );
		
		playVideo();
	
		txtslow.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
			
				// when play slow motion
				
				/*Intent intent = new Intent(onev.this,slow.class);
				getname = bb.getString("fname");
				intent.putExtra("fname", getname);
				startActivity(intent);
			*/
				// dont delete
				
				// play video slow motion in same intent
				
				if(flag==0) 
				{
									timer = new Timer(); 
									
								timer.schedule(new TimerTask() {
				
					                
					                public void run() {
					                	mVideoView.start();
					                }
					        }, 100, 200);
									timer.schedule(new TimerTask() {
				
					                
					                public void run() {
					                	mVideoView.pause();
					                }
					        }, 100, 200);
								
								flag=1;
								txtslow.setText("Normal View");			
				}
				
				else 
				{
					
					flag=0;
					txtslow.setText("Slow motion");
					 if(timer != null) {
						 timer.cancel();
						 mVideoView.start();
				        
				        timer = null;
				     }

				}
			}
		});
		
		
		mPause.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				
				if (mVideoView != null) {
					if(mVideoView.isPlaying())
					{
						mPause.setImageResource(R.drawable.playi);
						mVideoView.pause();
						if(timer!=null)
						{
							timer.cancel();
						}
					
						
					}
					else
					{
						mPause.setImageResource(R.drawable.pause);
						mVideoView.start();
						if(timer!=null)
						{
							timer.cancel();
						}
						
					}
				}
			}
		});
	
		mReset.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mVideoView != null) {
					mVideoView.seekTo(0);
					//mVideoView.start();
					//timer.cancel();
				}
			}
		});
		
		
		
		mfullscreen.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				if (mVideoView != null) {
					current = null;
					//mVideoView.stopPlayback();
					//timer.cancel();
				}
				Intent intent = new Intent(onev.this,video.class);
				intent.putExtra("fname", getname);
				startActivity(intent);
				
			}
		});
		runOnUiThread(new Runnable(){
			public void run() {
				playVideo();
				
			}
			
		});
	}

	
	public boolean onTouchEvent(MotionEvent event) {
			
		mtool = (LinearLayout) findViewById(R.id.mtool);
		mtool.setVisibility(0);
		
	    
		mpause();
		
		 
	
		return super.onTouchEvent(event);
	}


	private void mpause() {
		
		 t = new CountDownTimer( 3000 , 1000) {

	            public void onTick(long millisUntilFinished) {
	                Log.d("test","Timer tick");
	                   
	            }

	            public void onFinish() {
	                Log.d("test","Timer last tick");
	             
	                mtool.setVisibility(View.GONE);
	             
	            }
	         }.start();
		
	
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		 if ((keyCode == KeyEvent.KEYCODE_BACK)) 
		 {
				
				if(mVideoView.isPlaying()==true)
				{
					new AlertDialog.Builder(onev.this).setTitle("Close")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
						
						
							if(mVideoView.isPlaying())
							{
								mVideoView.stopPlayback();
							}
							
							Intent intent = new Intent(onev.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							finish();
							startActivity(intent);
							//finish();
						}
					}).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
						
							
						}
					}).show();
					
				}
				else
				{
		

					Intent intent = new Intent(onev.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					finish();
					startActivity(intent);

				}
		 }
		
		
		return super.onKeyDown(keyCode, event);
	}

	private void playVideo() {
		try {
			final String path = "a";
			Log.v(TAG, "path: " + path);
			if (path == null || path.length() == 0) {
				Toast.makeText(onev.this, "File URL/path is empty",
						Toast.LENGTH_LONG).show();

			} else {
				
				if (path.equals(current) && mVideoView != null) {
					mVideoView.start();
					mVideoView.requestFocus();
					return;
				}
				current = path;
				
				getname = bb.getString("fname");
		
				Toast.makeText(getApplicationContext(), getname, Toast.LENGTH_LONG).show();
				mVideoView.setVideoPath(getname);
				mVideoView.start();
			
				mVideoView.requestFocus();

			}
		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
			if (mVideoView != null) {
				mVideoView.stopPlayback();
			}
		}
	}

}
