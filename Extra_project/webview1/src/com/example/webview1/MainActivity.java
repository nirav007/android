package com.example.webview1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;

public class MainActivity extends Activity {

	private WebView webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		webview = (WebView) findViewById(R.id.webview);
		String videoPoP = "http://player.vimeo.com/video/27244727"; 
		webview.getSettings().setJavaScriptEnabled(true); 
		
		webview .getSettings().setPluginsEnabled(true);
		webview.getSettings().setAllowFileAccess(true);
		String widthAndHeight = "width=\"" + 400 + "\" height=\"" + 400 + "\""; 

		String temp = "<object " 
		+ widthAndHeight 
		+ ">" 
		+ "<body style='margin:0;padding:0;'>" 
		+ "<param name='allowFullScreen' value='false'>" 
		+ "</param><param name='allowscriptaccess' value='always'>" 
		+ "</param><embed src='" 
		+ videoPoP 
		+ "'" 
		+ " type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true'" 
		+ widthAndHeight + "></embed></object>"; 

		webview.loadData(temp, "text/html", "utf-8");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
