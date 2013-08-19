package com.example.asynctask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textView;

	/** Called when the activity is first created. */

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.TextView01);
	}

	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "Testing";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}
		
	 

		
		protected void onPostExecute(String result) {
			textView.setText(Html.fromHtml(result));
		}
	}

	public void readWebpage(View view) {
		DownloadWebPageTask task = new DownloadWebPageTask();
		task.execute(new String[] { "http://www.vogella.com" });

	}
}