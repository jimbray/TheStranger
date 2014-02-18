package com.bullshite.thestranger;



import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private WebView mWebView;
	private TextView mTvConnectFail;
	private boolean isLoadFail;
	private Button mBtnStartUp, mBtnStartBottom;
	private RelativeLayout mLayoutMain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//�����ޱ���  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //����ȫ��  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        
		setContentView(R.layout.activity_main);
		
		init();
		initAd();
		start();
	}
	
	private void initAd() {
		// 初始化应用的发布ID和密钥，以及设置测试模式
        AdManager.getInstance(this).init("a26e2fb6e3d6c696","609b4188ff8c35e8", false); 
        
     // Mini广告
        ((RelativeLayout)findViewById(R.id.mini_adLayout)).addView(new DiyBanner(this, DiyAdSize.SIZE_MATCH_SCREENx32));
        
     // 广告条接口调用        
        LinearLayout adLayout = (LinearLayout) findViewById(R.id.ad_bottombar_Layout);
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        adLayout.addView(adView);
	}
	
	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	private void init() {
		mTvConnectFail = (TextView) findViewById(R.id.tv_connect_failed);
		mTvConnectFail.setOnClickListener(this);
		
		mLayoutMain = (RelativeLayout) findViewById(R.id.layout_main);
		
		mBtnStartUp = (Button) findViewById(R.id.btn_start_up);
		mBtnStartBottom = (Button) findViewById(R.id.btn_start_bottom);
		mBtnStartUp.setOnClickListener(this);
		mBtnStartBottom.setOnClickListener(this);
		
		mBtnStartUp.setEnabled(false);
		mBtnStartBottom.setEnabled(false);
		
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

				mBtnStartUp.setText(R.string.start_chat);
				mBtnStartBottom.setText(R.string.start_chat);
				mBtnStartUp.setEnabled(true);
				mBtnStartBottom.setEnabled(true);
				
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
			case R.id.btn_start_up:
			case R.id.btn_start_bottom:
				mLayoutMain.setVisibility(View.GONE);
				mWebView.loadUrl("javascript:onStartChatClick()");
				break;
		}
	}

}
