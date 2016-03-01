package com.mygdx.livex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mygdx.game.R;

/**
 * Created by Radek on 2016-02-27.
 */
public class RegulaminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulamin);
        WebView mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.loadUrl("http://pharmawayjn.nazwa.pl/MedycynaRodzinna/gardimax/regulamin.html");
    }
}
