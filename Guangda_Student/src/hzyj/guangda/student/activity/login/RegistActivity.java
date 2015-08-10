package hzyj.guangda.student.activity.login;

import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetVerCodeResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.PhoneUtilLj;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @author liulj
 * 
 */
public class RegistActivity extends TitlebarActivity {
	private TextView mMobileTv, mCodeTv, mGetCodeTv;
	private EditText mMobileEt, mCodeEt;
	private ImageView mMobileLineIv, mCodeLineIv;
	private CountDownTimer timer = new CountDownTimer(60000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			mGetCodeTv.setText((millisUntilFinished / 1000) + "″后重获取");
		}

		@Override
		public void onFinish() {
			cancel();
			mGetCodeTv.setText("获取验证码");
			mGetCodeTv.setSelected(false);
		}
	};

	@Override
	public int getLayoutId() {
		return R.layout.regist_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mGetCodeTv = (TextView) findViewById(R.id.tv_get_code);

		mMobileTv = (TextView) findViewById(R.id.tv_mobile);
		mCodeTv = (TextView) findViewById(R.id.tv_code);

		mMobileEt = (EditText) findViewById(R.id.et_mobile);
		mCodeEt = (EditText) findViewById(R.id.et_code);

		mMobileLineIv = (ImageView) findViewById(R.id.iv_line_mobile);
		mCodeLineIv = (ImageView) findViewById(R.id.iv_line_code);

	}

	@Override
	public void addListeners() {
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mMobileEt.getText().toString().trim())) {
					showToast("请输入手机号！");
					return;
				}
				if (!PhoneUtilLj.isMobile(mMobileEt.getText().toString().trim())) {
					showToast("请输入正确的手机号码！");
					return;
				}
				if (TextUtils.isEmpty(mCodeEt.getText().toString().trim())) {
					showToast("请输入验证码！");
					return;
				}
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetVerCodeResponse.class, new MySubResponseHandler<GetVerCodeResponse>() {
					@Override
					public void onStart() {
						super.onStart();
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "VerificationCode");
						requestParams.add("phone", mMobileEt.getText().toString().trim());
						requestParams.add("type", "2");
						requestParams.add("code", mCodeEt.getText().toString().trim());
						return requestParams;
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, GetVerCodeResponse baseReponse) {
						showToast(baseReponse.getMessage());
						Intent intent = new Intent(mBaseFragmentActivity, SetAccountActivity.class);
						intent.putExtra("mPhone", mMobileEt.getText().toString().trim());
						startActivity(intent);
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
		mGetCodeTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mGetCodeTv.isSelected()) {
					if (TextUtils.isEmpty(mMobileEt.getText().toString().trim())) {
						showToast("请输入手机号！");
						return;
					}
					if (!PhoneUtilLj.isMobile(mMobileEt.getText().toString().trim())) {
						showToast("请输入正确的手机号码！");
						return;
					}
					timer.start();
					mGetCodeTv.setSelected(true);
					AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetVerCodeResponse.class, new MySubResponseHandler<GetVerCodeResponse>() {
						@Override
						public void onStart() {
							super.onStart();
							mLoadingDialog.show();
						}

						@Override
						public RequestParams setParams(RequestParams requestParams) {
							requestParams.add("action", "GetVerCode");
							requestParams.add("phone", mMobileEt.getText().toString().trim());
							requestParams.add("type", "3");
							return requestParams;
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers, GetVerCodeResponse baseReponse) {
							showToast(baseReponse.getMessage());
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
			}
		});
		mMobileEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mMobileLineIv.setSelected(true);
					mMobileTv.setSelected(true);
				} else {
					mMobileLineIv.setSelected(false);
					mMobileTv.setSelected(false);
				}
			}
		});
		mCodeEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mCodeLineIv.setSelected(true);
					mCodeTv.setSelected(true);
				} else {
					mCodeLineIv.setSelected(false);
					mCodeTv.setSelected(false);
				}
			}
		});
	}

	@Override
	public void initViews() {
		mCommonTitlebar.getCenterTextView().setText("手机验证");
		mCommonTitlebar.getRightTextView().setText("下一步");
	}

	@Override
	public void requestOnCreate() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

}
