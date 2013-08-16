package com.example.cameraupload2;



import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView imageView;
	private String picFileName;
	int c=1;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button photoButton = (Button) this.findViewById(R.id.button1);
        imageView = (ImageView)this.findViewById(R.id.imageView1);
        
        photoButton.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {

		long a=	System.currentTimeMillis() ;
		String f = String.valueOf(a).toString();
		//Toast.makeText(getApplicationContext(), f, Toast.LENGTH_LONG).show();
			 MyApplication appState = ((MyApplication)getApplicationContext());
			 appState.setSomeVariable(f.toString());
			   
			
				File file = new File(Environment.getExternalStorageDirectory()+"/"+f+".jpg");
			    Uri outputFileUri = Uri.fromFile(file);
			    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			    startActivityForResult(intent, 0);
				
			}
		});
    }
	public static String random() {
	    Random generator = new Random();
	    StringBuilder randomStringBuilder = new StringBuilder();
	    int randomLength = generator.nextInt(30);
	    char tempChar;
	    for (int i = 0; i < randomLength; i++){
	        tempChar = (char) (generator.nextInt(96) + 32);
	        randomStringBuilder.append(tempChar);
	    }
	    return randomStringBuilder.toString();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    Log.i("MakeMachine", "requestCode:"+requestCode + ",resultCode: " + resultCode);
	    
	    MyApplication appState = ((MyApplication)getApplicationContext());
	    String state = appState.getSomeVariable();
	    
	    Toast.makeText(getApplicationContext(), state, Toast.LENGTH_LONG).show();
	    
	                    File file = new File(Environment.getExternalStorageDirectory()+"/"+state+".jpg");
	                    if(file.exists()){
	                        BitmapFactory.Options options = new BitmapFactory.Options();
	                        options.inSampleSize = 4;
	                        picFileName=Environment.getExternalStorageDirectory()+"/"+state+".jpg";
	                        Toast.makeText(getApplicationContext(), picFileName, Toast.LENGTH_LONG).show();
	                        Bitmap bitmap = BitmapFactory.decodeFile(picFileName, options);
	                        imageView.setImageBitmap(bitmap);
	                        //imageView.setVisibility(View.VISIBLE);
	                        uploadPhoto(picFileName);
	                    }
	                

	}
	
	private void uploadPhoto(String picFileName)
	{
		HttpURLConnection conn = null;
	  	  DataOutputStream dos = null;
	  	  DataInputStream inStream = null; 

	  	 
	  	  String exsistingFileName = picFileName;
	

	  	  String lineEnd = "\r\n";
	  	  String twoHyphens = "--";
	  	  String boundary =  "*****";



	  	  int bytesRead, bytesAvailable, bufferSize;

	  	  byte[] buffer;

	  	  int maxBufferSize = 1*1024*1024;

	  	  //String urlString = "http://192.168.1.11/camera/upload.php";
	  	  String urlString = "http://wingstechsolutions.com/ts/android/Walker/upload.php";
	  	  
	  	  
	  	  
	  	  try
	  	  {
	  	   
	  	 
	  	  Log.e("MediaPlayer","Inside second Method");
	  	  Log.e("Camera","working");

	  	  FileInputStream fileInputStream = new FileInputStream(new File(exsistingFileName) );



	  	   URL url = new URL(urlString);

	  	   conn = (HttpURLConnection) url.openConnection();

	  	   conn.setDoInput(true);

	  	   // Allow Outputs
	  	   conn.setDoOutput(true);

	  	   // Don't use a cached copy.
	  	   conn.setUseCaches(false);

	  	   // Use a post method.
	  	   conn.setRequestMethod("POST");

	  	   conn.setRequestProperty("Connection", "Keep-Alive");
	  	 
	  	   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

	  	   
	  	   dos = new DataOutputStream( conn.getOutputStream() );

	  	   dos.writeBytes(twoHyphens + boundary + lineEnd);
	  	   dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + exsistingFileName +"\"" + lineEnd);
	  	   dos.writeBytes(lineEnd);

	  	   Log.e("MediaPlayer","Headers are written");



	  	   bytesAvailable = fileInputStream.available();
	  	   bufferSize = Math.min(bytesAvailable, maxBufferSize);
	  	   buffer = new byte[bufferSize];



	  	   bytesRead = fileInputStream.read(buffer, 0, bufferSize);

	  	   while (bytesRead > 0)
	  	   {
	  	    dos.write(buffer, 0, bufferSize);
	  	    bytesAvailable = fileInputStream.available();
	  	    bufferSize = Math.min(bytesAvailable, maxBufferSize);
	  	    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	  	   }

	  	   

	  	   dos.writeBytes(lineEnd);
	  	   
	  	   dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

	  	   BufferedReader in = new BufferedReader(
	  			   		new InputStreamReader(
	              		   conn.getInputStream()));
					String inputLine;
					
					while ((inputLine = in.readLine()) != null) 
						//tv.append(inputLine);

	  	   Log.e("MediaPlayer","File is written");
	  	   fileInputStream.close();
	  	   dos.flush();
	  	   dos.close();


	  	  }
	  	  catch (MalformedURLException ex)
	  	  {
	  	       Log.e("MediaPlayer", "error: " + ex.getMessage(), ex);
	  	  }

	  	  catch (IOException ioe)
	  	  {
	  	       Log.e("MediaPlayer", "error: " + ioe.getMessage(), ioe);
	  	  }

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
