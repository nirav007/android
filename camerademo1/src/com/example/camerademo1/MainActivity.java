package com.example.camerademo1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity 
{
	ImageView img;
	Button b1;
	File file;
	String f;
	Uri mCapturedImageURI;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img = (ImageView)findViewById(R.id.captured_imageView);
		
		b1 = (Button)findViewById(R.id.cam_button1);
		b1.setOnClickListener(new View.OnClickListener() 
		{	
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				file = new File(Environment.getExternalStorageDirectory(),  String.valueOf(System.currentTimeMillis()) + ".jpg"); 
				Log.e("ffffffffffiiiiiiiiilllllllllle ",""+file); // - this the captured image name
				f = String.valueOf(file);
				mCapturedImageURI = Uri.fromFile(file);
				Log.e("outputFileUri ",""+mCapturedImageURI);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI); 
				startActivityForResult(intent, 1); 	
			}
		});
	}

	 public void onActivityResult(int requestCode, int resultCode, final Intent data) 
     {
		  super.onActivityResult(requestCode, resultCode, data);
		  if(resultCode == Activity.RESULT_OK )
		  {
			  // Request Code for Profie Image Camera()
			  if(requestCode == 1)
			  {		    		 
				  Log.e("capturedImageFilePath ",""+f);
				  
				  Bitmap bMap = null;
				  BitmapFactory.Options options = new BitmapFactory.Options();
				  options.inSampleSize = 2;
				  bMap = BitmapFactory.decodeFile(f, options);
					
				  img.setBackgroundResource(0);
				  img.setImageBitmap(bMap);
				  
				  /*String url = "url where the image to be uploaded";
				  new ProfileAddImageFileAsync().execute(url);*/
			 }
        }			  
     }	  	  
	
	 @Override
	 protected Dialog onCreateDialog(int id) 
	 {
		  switch (id) 
		  {
	            case DIALOG_DOWNLOAD_PROGRESS:
	                mProgressDialog = new ProgressDialog(getParent());
	                mProgressDialog.setMessage("Uploading....");
	                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	                mProgressDialog.setCancelable(false);
	                mProgressDialog.show();
	                return mProgressDialog;
	            default:
	                return null;
		  }
	 }
	 
	 class ProfileAddImageFileAsync extends AsyncTask<String, String, String> 
	 {     				
	        @Override
	        protected void onPreExecute() 
	        {
	            super.onPreExecute();
	            showDialog(DIALOG_DOWNLOAD_PROGRESS);
	        }
	        
	        protected String doInBackground(String... aurl) 
	        {
	        	HttpURLConnection connection = null;
	        	DataOutputStream outputStream = null;
	        	DataInputStream inStream = null;
	        	try
	        	{
	        		URL urlServer = new URL(aurl[0]);
	        		Log.e("URl image uploading ",""+urlServer);
		    	
	        		String lineEnd = "\r\n";
	        		String twoHyphens = "--";
	        		String boundary =  "*****";
	        		int bytesRead, bytesAvailable, bufferSize;
	        		int maxBufferSize = 1*1024*1024;
	        		Log.e("maxBufferSize ",""+maxBufferSize);
       			
	        		FileInputStream fileInputStream = new FileInputStream(new File(f) );
	        		connection = (HttpURLConnection) urlServer.openConnection();
	        		connection.setDoInput(true);
	        		connection.setDoOutput(true);
	        		connection.setUseCaches(false);
	        		Log.e("FileInput Stream in image upload ",""+fileInputStream);
		    	
	        		connection.setRequestMethod("POST");
	        		connection.setRequestProperty("Connection", "Keep-Alive");
	        		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
	        		outputStream = new
	        		DataOutputStream( connection.getOutputStream() );
	        		outputStream.writeBytes(twoHyphens + boundary + lineEnd);
	        		outputStream.writeBytes("Content-Disposition: form-data; name=\"userfile\";filename=\"" + f +"\"" + lineEnd);
	        		outputStream.writeBytes(lineEnd);
	        		bytesAvailable = fileInputStream.available();
	        		Log.e("bytesAvailable ",""+bytesAvailable);
       			
	        		bufferSize = Math.min(bytesAvailable, maxBufferSize);
	        		Log.e("bufferSize ",""+bufferSize);
       			
	        		byte[] buffer = new byte[bufferSize];
	        		Log.e("bufer ",""+buffer);
	        		bytesRead = fileInputStream.read(buffer, 0, bufferSize);
       			
	        		while (bytesRead > 0)
	        		{
	        			outputStream.write(buffer, 0, bufferSize);
	        			bytesAvailable = fileInputStream.available();
	        			bufferSize = Math.min(bytesAvailable, maxBufferSize);
	        			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	        		}
	        		outputStream.writeBytes(lineEnd);
	        		outputStream.writeBytes(twoHyphens + boundary + twoHyphens +lineEnd);
       			
	        		@SuppressWarnings("unused")
					int serverResponseCode = connection.getResponseCode();
       			
	        		@SuppressWarnings("unused")
					String serverResponseMessage = connection.getResponseMessage();       
       			
	        		fileInputStream.close();
	        		outputStream.flush();
	        		outputStream.close();
	        	}
	        	catch (Exception ex)
	        	{
		       	 	Log.e("SD Card image upload error: ","" + ex.getMessage());
	        	}
	        	try 
	        	{
	        		inStream = new DataInputStream ( connection.getInputStream() );
	        		String str;
	        		while (( str = inStream.readLine()) != null)
	        		{
	        			Log.e("ImageResponse ",""+str); // respomse from ur server
	        		}
	        		inStream.close();
	        	}
	        	catch (IOException ioex)
	        	{
	        		Log.e("SD card doFile upload error: ","" + ioex.getMessage());    	
	        	}
	        	return null;
	        }
	        
	        protected void onProgressUpdate(String... progress) 
	        {
	        	 Log.e("ANDRO_ASYNC",""+progress[0]);
	        	 dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	        }

	        protected void onPostExecute(String unused) 
	        {
	        	Bitmap bMap = null;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				bMap = BitmapFactory.decodeFile(f, options);
				
				img.setBackgroundResource(0);
				img.setImageBitmap(bMap);
	        	
	        	dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	        }
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
