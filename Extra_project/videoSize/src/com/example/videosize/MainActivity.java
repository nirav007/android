package com.example.videosize;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity implements SurfaceHolder.Callback,
		OnPreparedListener, OnVideoSizeChangedListener {

	MediaPlayer mediaPlayer;
	SurfaceHolder surfaceHolder;
	SurfaceView playerSurfaceView;
	String videoSrc = "http://www.youtube.com/watch?v=JDaNekTvb9g";

	int videoWidth, videoHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		playerSurfaceView = (SurfaceView) findViewById(R.id.playersurface);

		surfaceHolder = playerSurfaceView.getHolder();
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {

		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setDataSource(videoSrc);
			mediaPlayer.prepare();
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		surfaceHolder.setFixedSize(videoWidth, videoHeight);
		mediaPlayer.start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		videoWidth = width;
		videoHeight = height;
		Toast.makeText(getApplicationContext(),
				String.valueOf(videoWidth) + "x" + String.valueOf(videoHeight),
				Toast.LENGTH_SHORT).show();

		if (mediaPlayer.isPlaying()) {
			surfaceHolder.setFixedSize(videoWidth, videoHeight);
		}
	}

}
