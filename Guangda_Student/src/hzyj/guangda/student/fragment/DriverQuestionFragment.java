package hzyj.guangda.student.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.MapHomeActivity;
import hzyj.guangda.student.view.NeedCityDialog;

public class DriverQuestionFragment extends Fragment{
	private View mBaseView;
	private Context mcontext;
	private ImageView iv_menu;
	private WebView wv_question;
	private String url="http://xiaobaxueche.com:8080/dadmin2.0.0/examination/index.jsp"; 
	//private String url="http://shop13287486.wxrrd.com";
	private MapHomeActivity homeActivity;
	private LinearLayout ll_title_bar;
	private RelativeLayout webviewError;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mcontext=getActivity();
		mBaseView = inflater.inflate(R.layout.xiaoba_driver_question,null);
		findView();
		getUrlData();
		return mBaseView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		homeActivity = (MapHomeActivity) activity;
	}
	
	public void findView(){
		wv_question=(WebView) mBaseView.findViewById(R.id.wv_question);
		iv_menu=(ImageView)mBaseView.findViewById(R.id.iv_question_menu);
		ll_title_bar=(LinearLayout)mBaseView.findViewById(R.id.ll_title_bar);
		webviewError=(RelativeLayout)mBaseView.findViewById(R.id.rl_webview_error);
		WebSettings s = wv_question.getSettings();    
		s.setBuiltInZoomControls(false);    
		s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);     
		s.setUseWideViewPort(true);     
		s.setLoadWithOverviewMode(true);    
		s.setSavePassword(true);     
		s.setSaveFormData(true);     
		s.setJavaScriptEnabled(true);     // enable navigator.geolocation     
		s.setGeolocationEnabled(true);     
		//s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage     
		s.setDomStorageEnabled(true);  
		
		wv_question.requestFocus();  
		

		iv_menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MapHomeActivity.mMenuIv.performClick();
			}
		});
		wv_question.setWebViewClient(new WebViewClient(){

		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        // TODO Auto-generated method stub
		    	  view.loadUrl(url);
		        //Toast.makeText(mcontext, url, 0).show();
		       //System.out.println(url);
		       if (!DriverQuestionFragment.this.url.equals(url))
		       {
		    	   homeActivity.rlBottom.setVisibility(View.GONE);
		    	   ll_title_bar.setVisibility(View.GONE);
		       }else{
		    	   homeActivity.rlBottom.setVisibility(View.VISIBLE);
		    	   ll_title_bar.setVisibility(View.VISIBLE);
		       }
		        return true;
		      }

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				
				//wv_question.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub
				
				super.onReceivedError(view, errorCode, description, failingUrl);
				webviewError.setVisibility(View.VISIBLE);
			}
		      
		      
		      
		    });	
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void getUrlData(){
		if(!url.equals("")){
			wv_question.loadUrl(url);
		}
	}
	
	
	

}
