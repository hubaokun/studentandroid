package hzyj.guangda.student;

import hzyj.guangda.student.activity.login.LoginActivity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.DensityUtils;
import com.common.library.llj.views.CommonTitlebar;

/**
 * 
 * @author liulj
 * 
 */
public abstract class TitlebarActivity extends BaseFragmentActivity {
	public CommonTitlebar mCommonTitlebar;

	@Override
	public void getIntentData() {

	}

	@Override
	public View getLayoutView() {
		ViewGroup rootView = null;
		if (getLayoutId() != 0) {
			rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.title_layout, null);
			initTitlebar(rootView);
			getLayoutInflater().inflate(getLayoutId(), rootView, true);
		}
		return rootView;
	}

	public boolean isLogin() {
		if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
			startMyActivity(LoginActivity.class);
			return false;
		} else {
			return true;
		}

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

	public void setLeftText(String str, int paddingLeft) {
		if (!TextUtils.isEmpty(str)) {
			mCommonTitlebar.getLeftTextView().setText(str);
			if (paddingLeft != 0) {
				paddingLeft = DensityUtils.dp2px(this, paddingLeft);
			}
			mCommonTitlebar.getLeftTextView().setPadding(paddingLeft, 0, 0, 0);
		}
	}

	public void setRightText(String str, int paddingRight) {
		if (!TextUtils.isEmpty(str)) {
			mCommonTitlebar.getRightTextView().setText(str);
			if (paddingRight != 0) {
				paddingRight = DensityUtils.dp2px(this, paddingRight);
			}
			mCommonTitlebar.getRightTextView().setPadding(0, 0, paddingRight, 0);
		}
	}

	public void setRightText(String str, int paddingRight, int color) {
		if (!TextUtils.isEmpty(str)) {
			mCommonTitlebar.getRightTextView().setText(str);
			if (paddingRight != 0) {
				paddingRight = DensityUtils.dp2px(this, paddingRight);
			}
			if (color != 0) {
				mCommonTitlebar.getRightTextView().setTextColor(getResources().getColor(color));
			}
			mCommonTitlebar.getRightTextView().setPadding(0, 0, paddingRight, 0);
		}
	}

	public void setCenterText(String str) {
		if (!TextUtils.isEmpty(str)) {
			mCommonTitlebar.getCenterTextView().setText(str);
		}
	}

	public void setLeftDrawable(int res, int paddingLeft) {
		mCommonTitlebar.getLeftTextView().setCompoundDrawablesWithIntrinsicBounds(res, 0, 0, 0);
		if (paddingLeft != 0) {
			paddingLeft = DensityUtils.dp2px(this, paddingLeft);
		}
		mCommonTitlebar.getLeftTextView().setPadding(paddingLeft, 0, 0, 0);
	}

	public void setRightDrawable(int res, int paddingRight) {
		mCommonTitlebar.getRightTextView().setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
		if (paddingRight != 0) {
			paddingRight = DensityUtils.dp2px(this, paddingRight);
		}
		mCommonTitlebar.getRightTextView().setPadding(0, 0, paddingRight, 0);
	}
}
