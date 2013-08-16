package com.newproject;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.newproject.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class signup extends Activity 
{
	
	private static final int SELECT_PICTURE = 1;
	 
    private String selectedImagePath = null;
    private ImageView img;
    String ss,getemail,getlocation;
	private TextView upload;

	private EditText fname,lname,email,pass;

	private TextView save;
	
	DataHendleCls obS = new DataHendleCls();
	int flag=0;
	private InputStream is;
	private StringBuilder sb;
	private String result;

	private TextView btnsignin;
    
	Bundle getdata;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.signup);
		
		init();
		
		
		save.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				
				// TODO Auto-generated method stub
				flag=0;
				String fname1  = fname.getText().toString().trim(); 
				String lname1  = lname.getText().toString().trim();
				String email1  = getemail.toString().trim();
				//Toast.makeText(getApplicationContext(), email1, Toast.LENGTH_LONG).show();
				
				String pass1  = pass.getText().toString().trim();
				
				
				if(selectedImagePath==null)
				{
					new AlertDialog.Builder(signup.this).setMessage("Select Image")
					.setTitle("Hotly Application")  
			           .setCancelable(true)
			           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).show();
				}
				else if(fname1.length()<=0 || lname.length()<=0  || pass1.length()<=0 )
				{
					new AlertDialog.Builder(signup.this).setMessage("Some Field are Missing !!")
					.setTitle("Hotly Application ")  
			           .setCancelable(true)
			           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
						
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					}).show();
				}
				//Toast.makeText(getApplicationContext(), lname1, Toast.LENGTH_LONG).show();
				
				else 
				{
					String checkmail  = email1.toString();
					if(checkEmail(checkmail)==true)
					{
						try
				        {
				        	HttpClient httpsClient = new DefaultHttpClient();
				        	HttpPost httpPost = new HttpPost(obS.insUrl);
				        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
				        	nameValue.add(new BasicNameValuePair("img", "" + selectedImagePath));
							nameValue.add(new BasicNameValuePair("fname", "" + fname1));
							nameValue.add(new BasicNameValuePair("lname", "" + lname1));
							nameValue.add(new BasicNameValuePair("email", "" + email1));
							nameValue.add(new BasicNameValuePair("pass", "" + pass1));
							nameValue.add(new BasicNameValuePair("location", "" + getlocation));
				        	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
				        	       	
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

						  	//  String urlString = "http://192.168.1.11/project/insert.php";
						  	String urlString = "http://192.168.1.4/services/insert.php";
						  	  
						  	  
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


						  	  try {
						  	        inStream = new DataInputStream ( conn.getInputStream() );
						  	        String str;
						  	       
						  	        while (( str = inStream.readLine()) != null)
						  	        {
						  	             Log.e("MediaPlayer","Server Response"+str);
						  	        }
						  	        
						  	        inStream.close();

						  	  }
						  	  catch (IOException ioex){
						  	       Log.e("MediaPlayer", "error: " + ioex.getMessage(), ioex);
						  	  }
						  	  
						  	HttpResponse httpResponce = httpsClient.execute(httpPost);
				        	HttpEntity httpEntity = httpResponce.getEntity();
				        	is = httpEntity.getContent();
				        }
				        catch (Exception e) { 	}
				        
				        try
				        {
				        	BufferedReader BufR = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
				        	sb = new StringBuilder();
				        	sb.append(BufR.readLine() + "\n");
				        	String line = "0";
				        	
				        	while((line = BufR.readLine()) != null)
				        	{
				        		sb.append(line + "\n");
				        	}
				        	
				        	is.close();
				        	result = sb.toString();
				        }
				        catch (Exception e) {
				        	Log.e("log_tag", "Error in convert String" + e.toString());
						}
				        

				        
				        try{
				        	
				        	JSONObject json_data = new JSONObject(result);
				        	
				        	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG ).show();
				        	//json_data = jArray.getJSONObject(0);
				        	
				        	String status = json_data.getString("Status");
				        	
				        	//Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG ).show();
				        	
				        	if(status.equals("OK"))
				        	{
				        		
				        		//Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_LONG).show();
				        		valid_login();
				        	}
				        	else
				        	{
				        		invalid_login();
				        	}
				        	
				        
				        }
				        catch (JSONException e){} 	
						
				        catch (Exception e){	}
					
						
				
					}
					else
					{
						new AlertDialog.Builder(signup.this).setMessage("Enter Valid Email Address !!")
						.setTitle("Hotly Application ")  
				           .setCancelable(true)
				           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
							
							
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						}).show();
					}
				}
				
			}

			

			private void instdata(String fname1,String lname1,String email1,String pass1) {
				try
		        {
					HttpClient httpsClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(obS.insUrl);
					
					List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
					nameValue.add(new BasicNameValuePair("img", "" + selectedImagePath));
					nameValue.add(new BasicNameValuePair("fname", "" + fname1));
					nameValue.add(new BasicNameValuePair("lname", "" + lname1));
					nameValue.add(new BasicNameValuePair("email", "" + email1));
					nameValue.add(new BasicNameValuePair("pass", "" + pass1));
					nameValue.add(new BasicNameValuePair("location", "" + getlocation));
					
					
					httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
					HttpResponse httpRes = httpsClient.execute(httpPost);
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

				  	  String urlString = "http://192.168.1.4/services/insert.php";
				  	  
				  	  
				  	  
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


				  	  try {
				  	        inStream = new DataInputStream ( conn.getInputStream() );
				  	        String str;
				  	       
				  	        while (( str = inStream.readLine()) != null)
				  	        {
				  	             Log.e("MediaPlayer","Server Response"+str);
				  	        }
				  	        
				  	        inStream.close();

				  	  }
				  	  catch (IOException ioex){
				  	       Log.e("MediaPlayer", "error: " + ioex.getMessage(), ioex);
				  	  }
					
					
					//finish();
		        	
		        }
		        catch (Exception e) {
				} 	 	
			}
		});
		
	        ((ImageView) findViewById(R.id.image))
	                .setOnClickListener(new OnClickListener() {
	        
	                	public void onClick(View arg0) {
	                        Intent intent = new Intent();
	                        intent.setType("image/*");
	                        intent.setAction(Intent.ACTION_GET_CONTENT);
	 
	                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
	                    }
	                });
	        
	        btnsignin.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent intent = new Intent(signup.this,New_project1Activity.class);
				startActivity(intent);
				}
			});
	       
	}

	private void init() {
				
		 img = (ImageView)findViewById(R.id.image);
		 btnsignin = (TextView) findViewById(R.id.btnsignin);
		 save = (TextView) findViewById(R.id.txtsave);
		 fname = (EditText) findViewById(R.id.edituser);
		 lname = (EditText) findViewById(R.id.editlast);
		 
		 pass = (EditText) findViewById(R.id.editepass);
		 
		 getdata = getIntent().getExtras();
		 
		 getemail = getdata.getString("email");
		 getlocation = getdata.getString("location");
		 
//		 Toast.makeText(getApplicationContext(), getlocation, Toast.LENGTH_LONG).show();
		 
		
	}
	
	public boolean checkEmail(String inputMail) 
	{   
	    Pattern pattern= Pattern.compile("^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");
	    return pattern.matcher(inputMail).matches();
	}
	

	public void valid_login()
	{
		new AlertDialog.Builder(signup.this).setMessage("Profile Created Successfully .")
		.setTitle("Hotly Application")  
           .setCancelable(true)
           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {

				
				Intent intent = new Intent(signup.this,New_project1Activity.class);
				startActivity(intent);
				
			}
		}).show();
		
	}
	public void invalid_login()
	{

		new AlertDialog.Builder(signup.this).setMessage("Email already exits .")
		.setTitle("Hotly Application ")  
           .setCancelable(true)
           .setNeutralButton("Ok",new DialogInterface.OnClickListener() {
			
			
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
				
			}
		}).show();
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setVisibility(0);
                img.setImageURI(selectedImageUri);
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
