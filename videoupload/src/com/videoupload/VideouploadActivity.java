package com.videoupload;

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
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import android.widget.TextView;

public class VideouploadActivity extends Activity {
    /** Called when the activity is first created. */
	TextView tv = null;
	Button upload;
	String ss;
	 private static final int SELECT_PICTURE = 1;
	 
	    private String selectedImagePath;
	    private ImageView img;
	    
		DataHendleCls obS = new DataHendleCls();
		
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        upload = (Button) findViewById(R.id.up);
        
        upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFileUpload();
				insdata(ss);
				
			}
		});
       
        
        img = (ImageView)findViewById(R.id.ImageView01);
        
        ((Button) findViewById(R.id.Button01))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                    }
                });
        
    }
    
    private void insdata(String msg) {
		// TODO Auto-generated method stub
		try
        {
		
			//Toast.makeText(ChatActivity.this, obS.insUrl, Toast.LENGTH_LONG).show();
			HttpClient httpsClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://192.168.1.5/upload.php");
			
			List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
			//Toast.makeText(getApplicationContext(), iuser.toString(), Toast.LENGTH_LONG).show();
			//nameValue.add(new BasicNameValuePair("id", " "));
			nameValue.add(new BasicNameValuePair("msg", "" + msg));
			
			
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
			
			HttpResponse httpRes = httpsClient.execute(httpPost);
			
			//finish();
        	
        }
        catch (Exception e) {
			// TODO: handle exception
        	//Log.e("log_tag", "Error in HTTP connection" + e.toString());
        	//Toast.makeText(ChatActivity.this, "http connection error : " + e.toString(), 
        	//		Toast.LENGTH_LONG).show();
		} 	 	
	}
    private void doFileUpload(){

    	  HttpURLConnection conn = null;
    	  DataOutputStream dos = null;
    	  DataInputStream inStream = null; 

    	 
    	  String exsistingFileName = ss;
    	  // Is this the place are you doing something wrong.

    	  String lineEnd = "\r\n";
    	  String twoHyphens = "--";
    	  String boundary =  "*****";


    	  int bytesRead, bytesAvailable, bufferSize;

    	  byte[] buffer;

    	  int maxBufferSize = 1*1024*1024;
  
    	  String urlString = "http://192.168.1.5/upload.php";
    	  
    	  
    	  
    	  try
    	  {
    	   
    	 
    	  Log.e("MediaPlayer","Inside second Method");

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
					tv.append(inputLine);

    	   
    	   
    	   
    	   // close streams
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


    	  //------------------ read the SERVER RESPONSE


    	  try {
    	        inStream = new DataInputStream ( conn.getInputStream() );
    	        String str;
    	       
    	        while (( str = inStream.readLine()) != null)
    	        {
    	             Log.e("MediaPlayer","Server Response"+str);
    	             
    	             
    	        }
    	        /*while((str = inStream.readLine()) !=null ){
    	        	
    	        }*/
    	        inStream.close();

    	  }
    	  catch (IOException ioex){
    	       Log.e("MediaPlayer", "error: " + ioex.getMessage(), ioex);
    	  }
    	  
    	  

    	}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
                Toast.makeText(getApplicationContext(), selectedImagePath, Toast.LENGTH_LONG).show();
                ss = selectedImagePath.replace("/mnt", "");
            }
        }
    }
 
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
