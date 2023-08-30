package com.example.vusse;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Maps extends Activity {
    String restoran_latitude, restoran_longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);

        WebView webview = (WebView) findViewById(R.id.webView1);
        restoran_latitude = getIntent().getStringExtra("restoran_latitude");
        restoran_longitude = getIntent().getStringExtra("restoran_longitude");
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        https://www.google.com/maps/search/-8.697692,+115.263766?shorturl=1
        webview.loadUrl("https://www.google.com/maps/search/"+restoran_latitude+","+restoran_longitude+"?shorturl=1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_nav, menu);
        return true;
    }
}