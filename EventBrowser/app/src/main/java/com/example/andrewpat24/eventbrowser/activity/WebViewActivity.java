package com.example.andrewpat24.eventbrowser.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.andrewpat24.eventbrowser.R;

public class WebViewActivity extends Activity {

    private WebView mWebViewActivity;

    public WebViewActivity(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle bundle = getIntent().getExtras();

        String url = bundle.getString("url");

        mWebViewActivity = (WebView) findViewById(R.id.activity_main_webview);

        mWebViewActivity.setWebViewClient(new WebViewClient());

        WebSettings webSettings = mWebViewActivity.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebViewActivity.loadUrl(url);

    }
}
