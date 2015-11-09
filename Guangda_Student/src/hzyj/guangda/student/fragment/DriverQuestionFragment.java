package hzyj.guangda.student.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.MapHomeActivity;
import hzyj.guangda.student.view.NeedCityDialog;

public class DriverQuestionFragment extends Fragment{
	private View mBaseView;
	private Context mcontext;
	private WebView wv_question;
	private String url="http://120.25.236.228/dadmin/examination/index.jsp"; 
	private MapHomeActivity homeActivity;

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
		wv_question.getSettings().setJavaScriptEnabled(true);
		wv_question.setWebViewClient(new WebViewClient(){

		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        // TODO Auto-generated method stub
		    	  view.loadUrl(url);
		       // Toast.makeText(mcontext, url, 0).show();
		       //System.out.println(url);
		       if (!DriverQuestionFragment.this.url.equals(url))
		       {
		    	   homeActivity.rlBottom.setVisibility(View.GONE);
		       }else{
		    	   homeActivity.rlBottom.setVisibility(View.VISIBLE);
		       }
		        return true;
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
