package hzyj.guangda.student.activity.setting;

import com.common.library.llj.base.BaseDialog;

import de.greenrobot.event.EventBus;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.RulerActivity;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.event.Update;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author liulj
 * 
 */
public class SettingActivity extends TitlebarActivity {
	private TextView mClearTv;
	private TextView mExitTv;
	private LinearLayout llComplaint;
	private LinearLayout llRuler;

	@Override
	public int getLayoutId() {
		return R.layout.setting_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mClearTv = (TextView) findViewById(R.id.tv_clear);
		mExitTv = (TextView) findViewById(R.id.tv_exit);
		llComplaint = (LinearLayout)findViewById(R.id.li_complaint);
		llRuler = (LinearLayout)findViewById(R.id.ll_rules);
	}

	@Override
	public void addListeners() {
		
		llRuler.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMyActivity(RulerActivity.class);
			}
		});
		mExitTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GuangdaApplication.mUserInfo.clearUserInfo();
				EventBus.getDefault().post(new Update("UserInfo"));
				startMyActivity(LoginActivity.class);
				finish();
			}
		});
		
		llComplaint.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
					startMyActivity(LoginActivity.class);
				} else {
					startMyActivity(MyComplaitActivity.class);
				}
			}
		});
	}

	@Override
	public void initViews() {
		setCenterText("设置");
		mClearTv.setText(getFileSize());
		if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
			mExitTv.setVisibility(View.INVISIBLE);
		} else {
			mExitTv.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void requestOnCreate() {

	}

	public void clearCach(View view) {
		new ClearDialog(this).show();
	}

	public void feedBack(View view) {
		if (isLogin())
			startMyActivity(FeedBackActivity.class);
	}

	public void aboutUs(View view) {
		startMyActivity(AboutUsActivity.class);
	}

	private class ClearDialog extends BaseDialog {

		public ClearDialog(Context context) {
			super(context, R.style.dim_dialog);
		}

		public ClearDialog(Context context, int theme) {
			super(context, R.style.dim_dialog);
		}

		public ClearDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
		}

		@Override
		protected int getLayoutId() {
			return R.layout.clear_dialog;
		}

		@Override
		protected void findViews() {
			findViewById(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					cleanCache();
					mClearTv.setText(getFileSize());
					dismiss();
				}
			});
			findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}

		@Override
		protected void setWindowParam() {
			setWindowParams(-1, -2, Gravity.CENTER);
		}

	}
}
