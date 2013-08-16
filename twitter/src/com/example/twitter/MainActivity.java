package com.example.twitter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {  
	 private ArrayList<Tweet> tweets = new ArrayList<Tweet>();  
	 
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        
        
        
        new MyTask().execute();  
    }  
      private class MyTask extends AsyncTask<Void, Void, Void> {  
              private ProgressDialog progressDialog;  
              protected void onPreExecute() {  
                      progressDialog = ProgressDialog.show(MainActivity.this,  
                                        "", "Loading. Please wait...", true);  
              }  
              
              protected Void doInBackground(Void... arg0) {  
            	  
            	  InputStream is = null;
            		StringBuilder sb;
            		String result1 = null;
            		
            		
            	  try
			        {
			        	HttpClient httpsClient = new DefaultHttpClient();
			        	HttpGet httpPost = new HttpGet("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=nirav_ranpara");
			        				        	
			        	HttpResponse httpResponce = httpsClient.execute(httpPost);
			        	HttpEntity httpEntity = httpResponce.getEntity();
			        	is = httpEntity.getContent();       	
			        	
			        }
			        catch (Exception e) {
			        	Log.e("log_tag", "Error in HTTP connection" + e.toString()); 	
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
			        	result1 = sb.toString();
			        	Log.e("Twitter",result1);
			        	Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_LONG).show();
			        }
			        catch (Exception e) {
			        	Log.e("log_tag", "Error in convert String" + e.toString());
					}
			        
			        try{
			        	
			        	
		        	 	JSONObject json_data = new JSONObject(result1);
		        	
		        
		        	
		        	String status = json_data.getString("Status");
		        	
		        	Log.e("" +
		        			"",result1);
		        	
		        	
			        }
			        catch(Exception e ){}
			        
            	  
                      try {  
                              HttpClient hc = new DefaultHttpClient();  
                              HttpGet get = new  
                              //HttpGet("http://search.twitter.com/search.json?q=android");  
                              HttpGet("http://search.twitter.com/search.json?q=boys_status&rpp=1000");
                              //HttpGet("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=nirav_ranpara");
                              HttpResponse rp = hc.execute(get);  
                             // if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
                              {  
                                      String result = EntityUtils.toString(rp.getEntity());  
                                      JSONObject root = new JSONObject(result);  
                                      JSONArray sessions = root.getJSONArray("results");  
                                      for (int i = 0; i < sessions.length(); i++) {  
                                              JSONObject session = sessions.getJSONObject(i);  
                                      Tweet tweet = new Tweet();  
                                               tweet.content = session.getString("text");  
                                               tweet.author = session.getString("from_user");  
                                               tweets.add(tweet);  
                                      }  
                             }  
                     } catch (Exception e) {  
                             Log.e("TwitterFeedActivity", "Error loading JSON", e);  
                     }  
                     return null;  
        }  
        protected void onPostExecute(Void result) {  
                progressDialog.dismiss();  
                setListAdapter(new TweetListAdaptor(  
                                MainActivity.this, R.layout.list_item, tweets));  
         }  
    }  
    private class TweetListAdaptor extends ArrayAdapter<Tweet> {  
            private ArrayList<Tweet> tweets;  
            public TweetListAdaptor(Context context,  
                                                                       int textViewResourceId,  
                                                                       ArrayList<Tweet> items) {  
                      super(context, textViewResourceId, items);  
                      this.tweets = items;  
            }  
              
            public View getView(int position, View convertView, ViewGroup parent) {  
                    View v = convertView;  
                    if (v == null) {  
                            LayoutInflater vi = (LayoutInflater)  
getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
                            v = vi.inflate(R.layout.list_item, null);  
                    }  
                    Tweet o = tweets.get(position);  
                    TextView tt = (TextView) v.findViewById(R.id.toptext);  
                    TextView bt = (TextView) v.findViewById(R.id.bottomtext);  
                    tt.setText(o.content);  
                    bt.setText(o.author);  
                    return v;  
            }  
       }  
}  
    
  /*  private void loadTweets(){  
        ArrayList<String> tweets = new ArrayList<String>();  
        try {  
                HttpClient hc = new DefaultHttpClient();  
                HttpGet get = new   HttpGet("http://search.twitter.com/search.json?q=android");  
                HttpResponse rp = hc.execute(get);  
              //  if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
                {  
                        String result = EntityUtils.toString(rp.getEntity());  
                        JSONObject root = new JSONObject(result);  
                        JSONArray sessions = root.getJSONArray("results");  
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        for (int i = 0; i < sessions.length(); i++) {  
                                JSONObject session = sessions.getJSONObject(i);  
                                tweets.add(session.getString("text"));
                                Toast.makeText(getApplicationContext(), " Tweet " + session.getString("text") +" . USer "+ session.getString("from_user"), Toast.LENGTH_LONG).show();
                                //Tweet tweet = new Tweet();  
                              //  tweet.content = session.getString("text");  
                              //  Toast.makeText(getApplicationContext(), tweet.content, Toast.LENGTH_LONG).show();
                               // tweet.author = session.getString("from_user");
                               // Toast.makeText(getApplicationContext(), tweet.author, Toast.LENGTH_LONG).show();
                               // tweets.add(tweet);  
                        }  
                }  
        } catch (Exception e) {  
                Log.e("TwitterFeedActivity", "Error loading JSON", e);  
        }  
          
}  */
    


