package com.example.jquery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WebView wv=(WebView)findViewById(R.id.webView1);
		wv.loadUrl("file:///android_asset/www/index.html");
		wv.getSettings().setJavaScriptEnabled(true);
	}

	

}
