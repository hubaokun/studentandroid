package hzyj.guangda.student.activity;

import android.os.Bundle;
import android.widget.TextView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;

public class RulerActivity extends TitlebarActivity {
	private TextView tvRuler;
	private String rulers;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.rules_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tvRuler = (TextView)findViewById(R.id.tv_rules);
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		rulers = getResources().getString(R.string.rules);
		tvRuler.setText(rulers);
		setCenterText("服务条款");
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}

}
