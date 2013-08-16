package com.chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends Activity {
    private Button btn;
	private EditText txt;
	DataHendleCls obS = new DataHendleCls();
	private ListView list;
	CountDownTimer t;
	private InputStream is;
	private StringBuilder sb;
	private String result;
	private JSONArray jArray;
	
	Bundle bb;
	String name;
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	private ArrayList<String> items = new ArrayList<String>();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        init();
        
        bb = getIntent().getExtras();
        name = bb.getString("name");
        t = new CountDownTimer( Long.MAX_VALUE , 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("test","Timer tick");
                
                select();
              
            }

            public void onFinish() {
                Log.d("test","Timer last tick");
              
                
            }
         }.start();
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				String msg =  name + " : " + txt.getText().toString();
				
				if(msg.length()>0)
				{
					insdata(msg);
					txt.setText(null);
				}
				select();
				
			}

			

			private void insdata(String msg) {
				// TODO Auto-generated method stub
				try
		        {
				
					//Toast.makeText(ChatActivity.this, obS.insUrl, Toast.LENGTH_LONG).show();
					HttpClient httpsClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(obS.insUrl);
					
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
		});
    }
    private void select() {
		// TODO Auto-generated method stub

		items.clear();
		  try
	        {
	        	HttpClient httpsClient = new DefaultHttpClient();
	        	HttpPost httpPost = new HttpPost(obS.sUrl);
	        	List<NameValuePair> nameValue = new ArrayList<NameValuePair>();
	        	httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
	        	HttpResponse httpResponce = httpsClient.execute(httpPost);
	        	HttpEntity httpEntity = httpResponce.getEntity();
	        	is = httpEntity.getContent();       	
	        	
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	Log.e("log_tag", "Error in HTTP connection" + e.toString());
	        	//Toast.makeText(ChatActivity.this, "http connection error : " + e.toString(), 
	        	//		Toast.LENGTH_LONG).show();
			}
	        
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
				// TODO: handle exception
	        	Log.e("log_tag", "Error in convert String" + e.toString());
			}
	        
	        String owner_name;
	        
	        
	        try{
	        	jArray = new JSONArray(result);
	        //	Toast.makeText(this, "no data found" + result, Toast.LENGTH_LONG).show();
	        	JSONObject json_data = null;
	        	for(int i = 0 ; i < jArray.length(); i++)
	        	{
	        		json_data = jArray.getJSONObject(i);
	        		//emp_id = json_data.getInt("id");
	        		owner_name = json_data.getString("msg");
	        		
	        		items.add("    " + owner_name );
	        		
	        		
	        		
	        	
	        	}
	        
	        	list.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,items));
	        	
	        	
	        	//setupList();
	        }
	        catch (JSONException e) {
	        	//Toast.makeText(ChatActivity.this, "no data found", Toast.LENGTH_LONG).show();
			}
	        catch (Exception e) {
	        	//Toast.makeText(ChatActivity.this, "error :" + e.toString(), Toast.LENGTH_LONG).show();
			}
	      
	}
	private void init() {
		// TODO Auto-generated method stub
		
		btn = (Button) findViewById(R.id.button1);
		txt = (EditText) findViewById(R.id.editText1);
		list = (ListView) findViewById(R.id.list);
	}
}