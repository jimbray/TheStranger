package com.bullshite.thestranger;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private WebView mWebView;
	private TextView mTvConnectFail;
	private boolean isLoadFail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//设置无标题  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //设置全屏  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        
		setContentView(R.layout.activity_main);
		
		init();
		start();
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void init() {
		mTvConnectFail = (TextView) findViewById(R.id.tv_connect_failed);
		mTvConnectFail.setOnClickListener(this);
		
		mWebView = (WebView) findViewById(R.id.webview);
		
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				return super.onJsAlert(view, url, message, result);
			}
			
		});
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				Log.d("bullshite", "====onPageStarted");
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
//				mWebView.loadUrl("javascript:onStartChatClick()");
//				mTvConnectFail.setVisibility(View.GONE);
				super.onPageFinished(view, url);
				Log.d("bullshite", "====onPageFinished");
				if(!isLoadFail) {
					mTvConnectFail.setVisibility(View.GONE);
				} 
				isLoadFail = false;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Log.d("bullshite", "====onReceivedError");
//				super.onReceivedError(view, errorCode, description, failingUrl);
				mTvConnectFail.setVisibility(View.VISIBLE);
				isLoadFail = true;
			}
			
			
		});
	}
	
	private void start() {
		String url = getResources().getString(R.string.url);
		mWebView.loadUrl(url);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.tv_connect_failed:
				start();
				break;
		}
	}

}
