package com.example.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
 
    WebView myWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("file:///android_asset/test.html");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
 
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
 
       // myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebViewClient(new MyWebViewClient());
    }
 
    public class JavaScriptInterface {
        Context mContext;
 
        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }
 
        /** Show a toast from the web page */
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }
    }
 
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.google.com")) {
                // This is my <span id="IL_AD8" class="IL_AD">web site</span>, so do not override; let my WebView load the page
                Toast.makeText(getApplicationContext(), "www.google.com", Toast.LENGTH_SHORT).show();
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
 
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the BACK key and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the BACK key or there's no web page history, <span id="IL_AD10" class="IL_AD">bubble up</span> to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}