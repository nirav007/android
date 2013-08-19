package com.example.htmltext;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TextView txt = (TextView) findViewById(R.id.txt);
        txt.setText(Html.fromHtml(getString(R.string.hello_world)));
        
        WebView webView = (WebView) findViewById(R.id.webview);
        
        String str="<html><body><font color='red'>A dressy take on classic gingham in a soft, textured weave of stripes that resembles twill.  Take a closer look at this one.<ul><li>Trim, tailored fit for a bespoke feel</li><li>Medium spread collar, one-button mitered barrel cuffs</li><li>Applied placket with genuine mother-of-pearl buttons</li><li>;Split back yoke, rear side pleats</li><li>Made in the U.S.A. of 100% imported cotton.</li></ul></font></body></html>";
        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
        webView.loadUrl("http://google.com/");
        webView.clearHistory();
        
    }

    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
