
package com.newproject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.newproject.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

public class setting extends Activity {

	String ss;
	 private static final int SELECT_PICTURE = 1;
	 
	    private String selectedImagePath;
	
	private TableRow rowpassword;
	private TableRow rowemail;
	private ImageView edit_image;
	private ImageView upload_img;
	DataHendleCls obS = new DataHendleCls();
	private String email,user_name,last_name,image_name=null;
	private TextView save_image;
	private TextView txtusername;
	private TableRow rowinterest;

	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting);
		
		init();
		
		//Toast.makeText(getApplicationContext(), image_name, Toast.LENGTH_LONG).show();
		if(image_name.length()>=200)
		{
			upload_img.setImageBitmap(getImageBitmap(obS.ImageString+image_name));
		}
		else
		{
			upload_img.setImageResource(R.drawable.profilepic_empty);
			//upload_img.setImageResource(android.R.drawable.btn_radio);
		}
		txtusername.setText(user_name.toString()+ " "+last_name.toString());
		
		rowpassword.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			
				Intent intent = new Intent(setting.this,settingtab.class);
				startActivity(intent);
			}
		});
		
		rowinterest.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(setting.this,settingtab_interest.class);
				startActivity(intent);
			}
		});
		
		rowemail.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(setting.this,settingtab_email.class);
				startActivity(intent);
			}
		});
		
		save_image.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
			
				update_image();
				FileUpload();	
			}		
		});
		
		((ImageView) findViewById(R.id.upload_img))
        .setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "z"),SELECT_PICTURE);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            }
        });
	}

	private void init() {
		// TODO Auto-generated method stub
		
		rowpassword= (TableRow) findViewById(R.id.rowpassword);
		rowemail= (TableRow) findViewById(R.id.rowemail);
		rowinterest     = (TableRow) findViewById(R.id.rowinterest);
		upload_img = (ImageView) findViewById(R.id.upload_img);
		
		save_image = (TextView)findViewById(R.id.btnupdateimg);
		
		txtusername =  (TextView)findViewById(R.id.txt_username);
		
		SharedPreferences sp = this.getSharedPreferences("data", MODE_WORLD_READABLE);
		email = sp.getString("globle_email", " ");
		user_name = sp.getString("user_name", " ");
		last_name = sp.getString("last_name", " ");
		image_name = sp.getString("image_name", "");
		
		
	}
	  private void update_image() {
			
			try
	        {
			
				HttpClient httpsClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(obS.passUrl);
				
				List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
				nameValue.add(new BasicNameValuePair("email", "" + email));
				nameValue.add(new BasicNameValuePair("imagename", "" + selectedImagePath));
				httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
				
				HttpResponse httpRes = httpsClient.execute(httpPost);
				
	        	
	        }
	        catch (Exception e) {		} 	 	
		}
	 
	  
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode == RESULT_OK) {
	            if (requestCode == SELECT_PICTURE) {
	                Uri selectedImageUri = data.getData();
	                selectedImagePath = getPath(selectedImageUri);
	                
	                System.out.println("Image Path : " + selectedImagePath);
	                upload_img.setImageURI(selectedImageUri);
	                //Toast.makeText(getApplicationContext(), selectedImagePath, Toast.LENGTH_LONG).show();
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
	    
	    public Bitmap getImageBitmap(String url) {
	        Bitmap bm = null;
	        URLConnection conn = null;
	        InputStream is = null;
	        BufferedInputStream bis =null;
	        try {
	            URL aURL = new URL(url);
	            
	            conn = aURL.openConnection();
	            
	            conn.connect();
	          is  = conn.getInputStream();
	          bis  = new BufferedInputStream(is);
	            bm = BitmapFactory.decodeStream(bis);
	            bis.close();
	            is.close();
	       } catch (IOException e) { }
	       return bm;
	    } 
		
	    private void FileUpload(){

	    	  HttpURLConnection conn = null;
	    	  DataOutputStream dos = null;
	    	  DataInputStream inStream = null; 

	    	 
	    	  String exsistingFileName = ss;
	    	  
	    	  
	    	 

	    	  String lineEnd = "\r\n";
	    	  String twoHyphens = "--";
	    	  String boundary =  "*****";


	    	  int bytesRead, bytesAvailable, bufferSize;

	    	  byte[] buffer;

	    	  int maxBufferSize = 1*1024*1024;
	  
	    	  String urlString = "http://192.168.1.4/services/upload.php";
	    	  
	  
	    	  try
	    	  {
	    	   	    	 
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
	    	  catch (Exception ex)
	    	  {
	    	       Log.e("MediaPlayer", "error: " + ex.getMessage(), ex);
	    	  }

	    	}
}
