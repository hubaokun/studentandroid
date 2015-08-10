package hzyj.guangda.student.activity;


import hzyj.guangda.student.R;
import android.os.Bundle;
import android.webkit.WebView;
import hzyj.guangda.student.TitlebarActivity;

public class ActivityMyCoinRule extends TitlebarActivity{
	private WebView coinRule;

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.coin_rule;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		coinRule=(WebView)findViewById(R.id.wv_rule);
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("使用规则");
		
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		coinRule.loadUrl("http://www.xiaobaxueche.com/coinrules.html");
		//setContentView(coinRule);
		
	}

	

}
