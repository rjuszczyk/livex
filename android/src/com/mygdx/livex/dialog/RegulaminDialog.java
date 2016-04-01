package com.mygdx.livex.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mygdx.livex.R;

import butterknife.Bind;

/**
 * Created by Radek on 2016-02-27.
 */
public class RegulaminDialog extends BaseDialog {

    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_regulamin;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mWebView = (WebView) mContentView.findViewById(R.id.web_view);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
