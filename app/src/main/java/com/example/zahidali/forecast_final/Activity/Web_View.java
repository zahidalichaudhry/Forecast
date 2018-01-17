package com.example.zahidali.forecast_final.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.zahidali.forecast_final.R;

public class Web_View extends AppCompatActivity {
    private WebView register;
    private ProgressBar progress;
    String WEB_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web__view);
        register=(WebView)findViewById(R.id.webView);
        progress=(ProgressBar)findViewById(R.id.progressBar);
        WebSettings webSettings=register.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Intent intent= getIntent();
        WEB_URL=intent.getStringExtra("weburl")+".html";
        register.loadUrl(WEB_URL);
        register.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
                setTitle("Loading....");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
                setTitle(view.getTitle());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Web_View.this,Home_Catogeries.class);
        startActivity(intent);
    }
}
