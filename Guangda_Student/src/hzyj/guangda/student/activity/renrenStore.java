package hzyj.guangda.student.activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.common.library.llj.base.BaseFragmentActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import hzyj.guangda.student.R;


public class renrenStore extends BaseFragmentActivity{
	
	private TextView tv_title;
	private LinearLayout ll_title_bar;
	private WebView wv_renren;
	private ImageView iv_back,iv_close;
	private String STORE_Url="http://shop13287486.wxrrd.com/";//http://shop13287486.wxrrd.com/

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.renren_store_webview;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tv_title=(TextView) findViewById(R.id.tv_title);
		ll_title_bar=(LinearLayout)findViewById(R.id.ll_title_bar);
		wv_renren=(WebView) findViewById(R.id.wv_renren);
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_close=(ImageView) findViewById(R.id.iv_close);
		iv_close.setVisibility(View.INVISIBLE);
		
		WebSettings s = wv_renren.getSettings();    
		s.setBuiltInZoomControls(true);    
		s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);     
		s.setUseWideViewPort(true);     
		s.setLoadWithOverviewMode(true);    
		s.setSavePassword(true);     
		s.setSaveFormData(true);     
		s.setJavaScriptEnabled(true);     // enable navigator.geolocation     
		s.setGeolocationEnabled(true);     
		//s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage     
		s.setDomStorageEnabled(true); 
		
		wv_renren.requestFocus();
		
		getUrlData();
		
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		
		
		iv_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(wv_renren.canGoBack()){
					wv_renren.goBack();
				}else{
					finish();
				}
			}
		});
		
		iv_close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		
		wv_renren.setWebViewClient(new WebViewClient(){

		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        // TODO Auto-generated method stub
		    	  //view.loadUrl(url);
		    	 
		    	  if(!renrenStore.this.STORE_Url.equals(url)){
		    		  iv_close.setVisibility(View.VISIBLE);
		    	  }else{
		    		  iv_close.setVisibility(View.INVISIBLE);
		    	  }
		        return true;
		      }

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}
			
			

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub
			} 
			

			
			@Override
			  public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
				try {
					final String a=url;
					renrenStore.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							 if(!renrenStore.this.STORE_Url.equals(a)){
					    		  iv_close.setVisibility(View.VISIBLE);
					    	  }else{
					    		  iv_close.setVisibility(View.INVISIBLE);
					    	  }
							
						}
					});
				} catch (Exception e) {
					// TODO: handle exception
				}


				return null;
			}
			
		    });
		
		
		

		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		 WebChromeClient wvcc = new WebChromeClient() {  
	            @Override  
	            public void onReceivedTitle(WebView view, String title) {  
	                super.onReceivedTitle(view, title);  
	                tv_title.setText(title);  
	            }  
	  
	        };  
	        
	        wv_renren.setWebChromeClient(wvcc);
	}
	
	public void getUrlData(){
		if(!STORE_Url.equals("")){
			wv_renren.loadUrl(STORE_Url);
		}
	}

}
