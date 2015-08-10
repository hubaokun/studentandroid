package com.common.library.llj.base;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.common.library.llj.R;
import com.common.library.llj.views.CommonTitlebar;

/**
 * 
 * @author liulj
 * 
 */
public abstract class BaseTitlebarActivity extends BaseFragmentActivity {
	public CommonTitlebar mCommonTitlebar;

	@Override
	public void getIntentData() {

	}

	@Override
	public View getLayoutView() {
		ViewGroup rootView = null;
		if (getLayoutId() != 0) {
			rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.base_title_layout, null);
			initTitlebar(rootView);
			getLayoutInflater().inflate(getLayoutId(), rootView, true);
		}
		return rootView;
	}

	private void initTitlebar(View view) {
		mCommonTitlebar = (CommonTitlebar) view.findViewById(R.id.titlebar);
		mCommonTitlebar.setLeftTextOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
