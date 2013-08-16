package com.example.videorecord1;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private final int VIDEO_RESULT = 1;
	private ArrayList<String> selectedVideos;
	private Uri uriVideo;
	private String selectedImagePath;
	private VideoView vv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button btnOk = (Button) findViewById(R.id.btn_take_photo);
		
		Button btnplay = (Button) findViewById(R.id.btn_play);
		
		btnplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(MainActivity.this,onev.class);
				intent.putExtra("fname", selectedImagePath);
				startActivity(intent);
				
			}
		});

		vv = (VideoView) findViewById(R.id.vv);

		btnOk.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// mpopup.dismiss(); //dismissing the popup
				// mpopup.dismiss();
				PackageManager pm = getPackageManager();
				if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

					try {
						Intent i = new Intent(
								android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
						// i.putExtra(MediaStore.EXTRA_OUTPUT,MyFileContentProviderVideo.CONTENT_URI);
						startActivityForResult(i, VIDEO_RESULT);

					} catch (Exception e) {

					}
				} else {
					Toast.makeText(getBaseContext(), "Camera is not available",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		selectedVideos = new ArrayList<String>();

		if (resultCode == RESULT_OK && requestCode == VIDEO_RESULT) {
			try {

				uriVideo = data.getData();
				// Toast.makeText(ProfileVideo.this,uriVideo.getPath(),
				// Toast.LENGTH_LONG).show();

				selectedImagePath = uriVideo.getPath();

				//Toast.makeText(getApplicationContext(), selectedImagePath,
				//		Toast.LENGTH_LONG).show();

				// selectedImages = new ArrayList<String>();

				byte[] ba = getCapturedVideoStream(MainActivity.this, data);
				MyWrite(ba);

				// selectedImagePath = Base64.encodeBytes(ba);
				selectedVideos.add(selectedImagePath);
				PLayMyVideo();
				
				Button btnplay = (Button) findViewById(R.id.btn_play);
				btnplay.setVisibility(View.VISIBLE);
				btnplay.setClickable(true);

			} catch (Exception e) {

			}

		}
	}

	public static byte[] getCapturedVideoStream(Context ctx, Intent data) {
		try {
			AssetFileDescriptor videoAsset = ctx.getContentResolver()
					.openAssetFileDescriptor(data.getData(), "r");
			FileInputStream fis = videoAsset.createInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;)
					bos.write(buf, 0, readNum);
			} catch (IOException e) {
				// CommonFunctions.writeLOG(CacheImagesManager.class.getClass().toString(),
				// e.toString());
			}
			byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (IOException e) {
			// CommonFunctions.writeLOG(CacheImagesManager.class.getClass().toString(),
			// e.toString());
			return null;
		}
	}

	public void MyWrite(byte[] buffer) {
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
		directory.mkdirs();
		// Now create the file in the above directory and write the contents
		// into it
		File file = new File(directory, "sample.mp4");
		selectedImagePath = file.getAbsolutePath();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedOutputStream osw = new BufferedOutputStream(fOut);
		try {
			// osw.write(path);
			osw.write(buffer);
			// osw.write(buffer, offset, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			osw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void PLayMyVideo() {

		try {
			/*
			 * vv.setVideoPath(url); vv.setMediaController(new
			 * MediaController(this)); vv.start();
			 */

			MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(vv);
			vv.setMediaController(mediaController);
			vv.setVideoURI(Uri.parse(selectedImagePath));
			vv.start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}