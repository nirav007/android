package com.video1;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class Video1Activity extends Activity {
	
	final static int REQUEST_VIDEO_CAPTURED = 1;
	Uri uriVideo = null;
	VideoView videoviewPlay;
    

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button buttonRecording = (Button)findViewById(R.id.recording);
        Button buttonPlayback = (Button)findViewById(R.id.playback);
        videoviewPlay = (VideoView)findViewById(R.id.videoview);
        
        buttonRecording.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
				startActivityForResult(intent, REQUEST_VIDEO_CAPTURED);
			}});
        
        buttonPlayback.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(uriVideo == null){
					Toast.makeText(Video1Activity.this, 
							"No Video", 
							Toast.LENGTH_LONG)
							.show();
				}else{
					Toast.makeText(Video1Activity.this, 
							"Playback: " + uriVideo.getPath(), 
							Toast.LENGTH_LONG)
							.show();
					videoviewPlay.setVideoURI(uriVideo);
					videoviewPlay.start();
				}
			}});
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			if(requestCode == REQUEST_VIDEO_CAPTURED){
				uriVideo = data.getData();
				Toast.makeText(Video1Activity.this, 
						uriVideo.getPath(), 
						Toast.LENGTH_LONG)
						.show();
			}
		}else if(resultCode == RESULT_CANCELED){
			uriVideo = null;
			Toast.makeText(Video1Activity.this, 
					"Cancelled!", 
					Toast.LENGTH_LONG)
					.show();
		}
	}
}