package hzyj.guangda.student.activity.setting;

import com.common.library.llj.utils.PackageMangerUtilLj;

import android.os.Bundle;
import android.widget.TextView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;

/**
 * 关于我们
 * 
 * @author liulj
 * 
 */
public class AboutUsActivity extends TitlebarActivity {
	private TextView mVersionTv;

	@Override
	public int getLayoutId() {
		return R.layout.about_us_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mVersionTv = (TextView) findViewById(R.id.tv_version);
	}

	@Override
	public void addListeners() {

	}

	@Override
	public void initViews() {
		setCenterText("关于我们");
		mVersionTv.setText("V " + PackageMangerUtilLj.getAppVersionName(this));

	}

	@Override
	public void requestOnCreate() {

	}

}
