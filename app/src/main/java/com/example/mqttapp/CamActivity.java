package com.example.mqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.marcoscg.ipcamview.IPCamView;

public class CamActivity extends AppCompatActivity {

    WebView webViewCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        webViewCam =(WebView) findViewById(R.id.webViewCam);

        webViewCam.setWebViewClient(new WebViewClient());
        webViewCam.getSettings().setMinimumFontSize(12);
        webViewCam.getSettings().setJavaScriptEnabled(true);
        webViewCam.getSettings().setLoadWithOverviewMode(true);
        webViewCam.getSettings().setUseWideViewPort(true);
        webViewCam.getSettings().setSupportZoom(true);
        webViewCam.getSettings().setBuiltInZoomControls(true);
        webViewCam.getSettings().setDisplayZoomControls(false);
        webViewCam.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webViewCam.setScrollbarFadingEnabled(false);
        String newUA= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12) AppleWebKit/602.1.50 (KHTML, like Gecko) Version/10.0 Safari/602.1.50";
        webViewCam.getSettings().setUserAgentString(newUA);

        webViewCam.loadUrl("http://192.168.1.242");

    }
}