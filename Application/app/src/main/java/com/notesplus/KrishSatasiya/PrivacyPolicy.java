package com.notesplus.KrishSatasiya;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrivacyPolicy extends AppCompatActivity {
private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        webview = findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://krishsatasiya.netlify.app/privacy-policy/privacy-policy");
    }


}