package hzyj.guangda.student.activity.login;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.MapHomeActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.LoginResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TextUtilLj;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 账号设置
 * 
 * @author liulj
 * 
 */
public class SetAccountActivity extends TitlebarActivity {
	private TextView mNameTv, mWordTv;
	private EditText mNameEt, mWordEt;
	private ImageView mNameLineIv, mWordLineIv;
	private String mPhone;

	@Override
	public int getLayoutId() {
		return R.layout.set_account_activity;
	}

	@Override
	public void getIntentData() {
		mPhone = getIntent().getStringExtra("mPhone");
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mNameTv = (TextView) findViewById(R.id.tv_name);
		mWordTv = (TextView) findViewById(R.id.tv_word);

		mNameEt = (EditText) findViewById(R.id.et_name);
		mWordEt = (EditText) findViewById(R.id.et_word);

		mNameLineIv = (ImageView) findViewById(R.id.iv_line_name);
		mWordLineIv = (ImageView) findViewById(R.id.iv_line_word);

		mWordTv.getLayoutParams().width = (int) TextUtilLj.getTextViewLength(mNameTv, "真实姓名");
	}

	@Override
	public void addListeners() {
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mNameEt.getText().toString().trim())) {
					showToast("请输入真实姓名！");
					return;
				}
				if (TextUtils.isEmpty(mWordEt.getText().toString().trim())) {
					showToast("请输入密码！");
					return;
				}
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, LoginResponse.class, new MySubResponseHandler<LoginResponse>() {
					@Override
					public void onStart() {
						super.onStart();
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {

						requestParams.add("action", "Register");
						requestParams.add("phone", mPhone);
						requestParams.add("realname", mNameEt.getText().toString().trim());
						if (!TextUtils.isEmpty(mWordEt.getText().toString().trim()))
							requestParams.add("password", TextUtilLj.md5(mWordEt.getText().toString().trim()));
						return requestParams;
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, LoginResponse baseReponse) {
						showToast(baseReponse.getMessage());
						if (baseReponse.getUserInfo() != null) {
							GuangdaApplication.mUserInfo.saveUserInfo(baseReponse.getUserInfo());
							EventBus.getDefault().post(new Update("UserInfo"));
						}
						startMyActivity(MapHomeActivity.class);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

					}

					@Override
					public void onFinish() {
						super.onFinish();
						mLoadingDialog.dismiss();
					}
				});

			}
		});
		mNameEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mNameLineIv.setSelected(true);
					mNameTv.setSelected(true);
				} else {
					mNameLineIv.setSelected(false);
					mNameTv.setSelected(false);
				}
			}
		});
		mWordEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mWordLineIv.setSelected(true);
					mWordTv.setSelected(true);
				} else {
					mWordLineIv.setSelected(false);
					mWordTv.setSelected(false);
				}
			}
		});
	}

	@Override
	public void initViews() {
		mCommonTitlebar.getCenterTextView().setText("账号设置");
		mCommonTitlebar.getRightTextView().setText("提交");
	}

	@Override
	public void requestOnCreate() {

	}

}
