package com.video;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class VideorecordActivity extends Activity implements SurfaceHolder.Callback {
    MediaRecorder mRecorder;
    SurfaceHolder mHolder;
    SurfaceView mSurfaceView; 
    String mOutputFileRoot = "/sdcard/Avid_";
    String mOutputFile; 
    String mFileExt = ".3gp"; 
    Integer cnt = 0; 
     private boolean mRecording = false; 

     /** Called when the activity is first created. */ 
     @Override 
     public void onCreate(Bundle savedInstanceState) { 
          super.onCreate(savedInstanceState); 
          requestWindowFeature(Window.FEATURE_NO_TITLE);
          getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
          setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
          setContentView(R.layout.main); 

          mSurfaceView =  ((SurfaceView)findViewById(R.id.camera_preview));
          mHolder = mSurfaceView.getHolder();
          mHolder.addCallback(this);
          mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

     } 

     @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) 
     { 
         if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) 
         { 
          if (mRecording) { 
                stopRecording();
                //finish(); 
             if(mRecorder == null){
                    initMediaRecorder();
                    prepareMediaRecorder();
                    }
                mRecording = false;
            } else { 
                mRecording = true; 

                startRecording(); 
            } 
             return true; 
         } 
         return super.onKeyDown(keyCode, event); 
     }   

     public void surfaceCreated(SurfaceHolder holder) {
         mHolder = holder;
         initMediaRecorder();
         prepareMediaRecorder();

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mHolder != null) mHolder = null; 
        if(mSurfaceView != null) mSurfaceView = null; 
    }

    public void initMediaRecorder(){

        mRecorder = new MediaRecorder();

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
        mRecorder.setPreviewDisplay(mHolder.getSurface());
        mOutputFile = mOutputFileRoot + cnt.toString() + mFileExt;
        cnt += 1;
        mRecorder.setOutputFile(mOutputFile);

    }

    private void prepareMediaRecorder(){
        if (mRecorder != null) {
            try {
                mRecorder.prepare();
            } catch (IllegalStateException e) {
                Log.e("IllegalStateException", e.toString());
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
    } 

     public void startRecording()
     {
        mRecorder.start();
     }

     public void stopRecording()
     {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;   
     }

     @Override
     public void onPause(){
         if(mRecorder != null){
             mRecorder.reset();
             mRecorder.release();
             mRecorder = null; 
         }
         super.onPause();
     }
    }