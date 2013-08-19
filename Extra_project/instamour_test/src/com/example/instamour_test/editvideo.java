package com.example.instamour_test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class editvideo extends Activity {

	private final int VIDEO_RESULT = 1;
	private LinearLayout video_intro;
	private String selectedImagePath;
	private ArrayList<String> selectedVideos;
	private Uri uriVideo;
	private ImageView img_videointro;
	private TextView txt_videointro;
	private LinearLayout video_pick;
	private LinearLayout video_place;
	private LinearLayout video_pet;
	private String detectVideo;
	private ImageView img_videopet;
	private ImageView img_videoplace;
	private ImageView img_videopick;
	
	function c = new function();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.createvideo);

		video_intro = (LinearLayout) findViewById(R.id.video_intro);
		video_pick = (LinearLayout) findViewById(R.id.video_pick);
		video_place = (LinearLayout) findViewById(R.id.video_place);
		video_pet = (LinearLayout) findViewById(R.id.video_pet);
		img_videointro = (ImageView) findViewById(R.id.img_videointro);
		img_videopet = (ImageView) findViewById(R.id.img_videopet);
		img_videoplace = (ImageView) findViewById(R.id.img_videoplace);
		img_videopick = (ImageView) findViewById(R.id.img_videopick);
		txt_videointro = (TextView) findViewById(R.id.txt_videointro);

		video_intro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detectVideo = "1";
				getCamera();

			}
		});

		video_pick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detectVideo = "4";
				getCamera();
			}
		});

		video_place.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detectVideo = "3";
				getCamera();
			}
		});

		video_pet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detectVideo = "2";
				getCamera();
			}
		});

	}

	public void getCamera() {
		PackageManager pm = getPackageManager();
		if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

			try {
				Intent i = new Intent(
						android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
				i.putExtra("android.intent.extra.durationLimit", 8);
				// i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);

				startActivityForResult(i, VIDEO_RESULT);

			} catch (Exception e) {

			}
		} else {
			Toast.makeText(getBaseContext(), "Camera is not available",
					Toast.LENGTH_LONG).show();
		}

	}

	@SuppressLint("NewApi")
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		selectedVideos = new ArrayList<String>();

		if (resultCode == RESULT_OK && requestCode == VIDEO_RESULT) {
			try {

				uriVideo = data.getData();
				/*Toast.makeText(createvideo.this, uriVideo.getPath(),
						Toast.LENGTH_LONG).show();*/

				selectedImagePath = uriVideo.getPath();
				// selectedImages = new ArrayList<String>();

				byte[] ba = getCapturedVideoStream(editvideo.this, data);
				MyWrite(ba);

				// selectedImagePath = Base64.encodeBytes(ba);
				selectedVideos.add(selectedImagePath);
				/*Toast.makeText(createvideo.this, selectedImagePath,
						Toast.LENGTH_LONG).show();*/

				Bitmap thumb = ThumbnailUtils.createVideoThumbnail(
						selectedImagePath,
						MediaStore.Images.Thumbnails.MICRO_KIND);
				/*
				 * Drawable d = new BitmapDrawable(getResources(),thumb);
				 * //video_intro.setBackgroundDrawable(d); //int height =
				 * img_videointro.getHeight(); //int width =
				 * img_videointro.getWidth();
				 * img_videointro.setImageBitmap(thumb); d =
				 * getResources().getDrawable(R.drawable.videowith);
				 * img_videointro.setBackgroundDrawable(d);
				 * img_videointro.setPadding(0,10,0,0);
				 */
				// img_videointro.getLayoutParams().height = height;
				// img_videointro.getLayoutParams().width = width;
				// LinearLayout.LayoutParams layoutParams = new
				// LinearLayout.LayoutParams(video_intro.getWidth()-10,
				// video_intro.getHeight()-10);
				// video_intro.setLayoutParams(layoutParams);
				// video_intro.setText("");
				int height = img_videointro.getHeight();
				int width = img_videointro.getWidth();
				if (detectVideo.equals("1")) {

					uploadVideo(
							c.link.toString()+"/video1.php",
							"video1");
					Drawable d = new BitmapDrawable(getResources(), thumb);
					img_videointro.setImageBitmap(thumb);
					d = getResources().getDrawable(R.drawable.videowith);
					img_videointro.setBackgroundDrawable(d);
					img_videointro.setPadding(0, 10, 0, 0);
					img_videointro.setMaxHeight(height);
					img_videointro.setMaxWidth(width);
				} else if (detectVideo.equals("2")) {
					uploadVideo(
							c.link.toString()+"/video2.php",
							"video2");
					Drawable d = new BitmapDrawable(getResources(), thumb);
					img_videopet.setImageBitmap(thumb);
					d = getResources().getDrawable(R.drawable.videowith);
					img_videopet.setBackgroundDrawable(d);
					img_videopet.setPadding(0, 10, 0, 0);
					img_videopet.setMaxHeight(height);
					img_videopet.setMaxWidth(width);
				} else if (detectVideo.equals("3")) {
					uploadVideo(
							c.link.toString()+"/video3.php",
							"video3");
					Drawable d = new BitmapDrawable(getResources(), thumb);
					img_videoplace.setImageBitmap(thumb);
					d = getResources().getDrawable(R.drawable.videowith);
					img_videoplace.setBackgroundDrawable(d);
					img_videoplace.setPadding(0, 10, 0, 0);
					img_videoplace.setMaxHeight(height);
					img_videoplace.setMaxWidth(width);

				} else if (detectVideo.equals("4")) {
					uploadVideo(
							c.link.toString()+"/video4.php",
							"video4");
					Drawable d = new BitmapDrawable(getResources(), thumb);
					img_videopick.setImageBitmap(thumb);
					d = getResources().getDrawable(R.drawable.videowith);
					img_videopick.setBackgroundDrawable(d);
					img_videopick.setPadding(0, 10, 0, 0);
					img_videopick.setMaxHeight(height);
					img_videopick.setMaxWidth(width);
				}

			} catch (Exception e) {

			}
		}

	}

	@SuppressLint("NewApi")
	public void uploadVideo(String URL, String parameter) {
		int SDK_INT = android.os.Build.VERSION.SDK_INT;

		if (SDK_INT > 8) {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

		}

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(editvideo.this);
		String user_id = preferences.getString("uid", "");
		Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG)
				.show();

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL);

		/*Toast.makeText(getApplicationContext(), "here:" + selectedImagePath,
				Toast.LENGTH_LONG).show();*/
		File file = new File(selectedImagePath);

		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		try {
			FormBodyPart userFile = new FormBodyPart(parameter, new FileBody(
					file, "video/*"));
			// userFile.addField(Environment.getExternalStorageDirectory()+"/a.png","NEWNAMEOFILE.png");
			mpEntity.addPart(userFile);
			// mpEntity.addPart("photo", new FileBody(file, "image/png"));
			mpEntity.addPart("uid", new StringBody(user_id));
			mpEntity.addPart("count", new StringBody("2"));

		} catch (Exception e) {

		}

		httppost.setEntity(mpEntity);
		HttpResponse response;
		try {
			// Log.d(TAG, "UPLOAD: about to execute");
			response = httpclient.execute(httppost);
			// /Log.d(TAG, "UPLOAD: executed");
			HttpEntity resEntity = response.getEntity();
			// Log.d(TAG, "UPLOAD: respose code: " +
			// response.getStatusLine().toString());
			// if (resEntity != null) {
			// Log.d(TAG, "UPLOAD: " + EntityUtils.toString(resEntity));
			// }
			if (resEntity != null) {
				resEntity.consumeContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		httppost.setEntity(mpEntity);

	}

	public void MyWrite(byte[] buffer) {

		File sdCard = Environment.getExternalStorageDirectory();
		// File directory = new File(sdCard.getAbsolutePath() + "/Music" +
		// "");
		// directory.mkdirs();

		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/instamour");
		boolean success = true;
		if (!directory.exists()) {
			success = directory.mkdir();
		}

		// Now create the file in the above directory and write the contents
		// into it
		long seconds = System.currentTimeMillis() / 1000l;
		Date lm = new Date();
		String lasmod = new SimpleDateFormat("yyyyMMdd").format(lm);
		String videoname = lasmod + String.valueOf(seconds) + "_video" + ".mp4";
		File file = new File(directory, videoname);
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

}
