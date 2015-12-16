package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.BindAliAccountResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.common.library.llj.listener.OnMyTextWatcher;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

/**
 * 绑定支付宝账户
 * 
 * @author liulj
 * 
 */
public class BandAliAcountActivity extends TitlebarActivity {
	private EditText mAccountEt;
	private String mPreAccountStr;

	@Override
	public int getLayoutId() {
		return R.layout.band_aliacount_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mAccountEt = (EditText) findViewById(R.id.et_account);
		
	}

	@Override
	public void addListeners() {
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mCommonTitlebar.getRightTextView().isSelected())
					AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SMY_URL, BindAliAccountResponse.class, new MySubResponseHandler<BindAliAccountResponse>() {
						@Override
						public void onStart() {
							super.onStart();
							mLoadingDialog.show();
						}

						@Override
						public RequestParams setParams(RequestParams requestParams) {
							requestParams.add("action", "ChangeAliAccount");
							requestParams.add("userid", GuangdaApplication.mUserInfo.getStudentid());
							requestParams.add("type", "2");
							requestParams.add("aliaccount", mAccountEt.getText().toString().trim());
							return requestParams;
						}

						@Override
						public void onFinish() {
							mLoadingDialog.dismiss();
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers, BindAliAccountResponse baseReponse) {
							if (baseReponse != null) {
								GuangdaApplication.mUserInfo.setAlipay_account(baseReponse.getAliacount());
							}
							showToast("绑定成功");
							finish();
						}

					});
			}
		});
		mAccountEt.addTextChangedListener(new OnMyTextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if ("".equals(s.toString()) || s.toString().equals(mPreAccountStr)) {
					mCommonTitlebar.getRightTextView().setSelected(true);

				} else {
					mCommonTitlebar.getRightTextView().setSelected(false);
				}
			}
		});
	}

	@Override
	public void initViews() {
		mPreAccountStr = GuangdaApplication.mUserInfo.getAlipay_account();
		setText(mAccountEt, mPreAccountStr);
		setCenterText("管理账户");
		setRightText("提交", 10);
		mCommonTitlebar.getRightTextView().setSelected(true);
	}

	@Override
	public void requestOnCreate() {

	}

//	public void onClearAccount(View view) {
//		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SMY_URL, BindAliAccountResponse.class, new MySubResponseHandler<BindAliAccountResponse>() {
//			@Override
//			public void onStart() {
//				super.onStart();
//				mLoadingDialog.show();
//			}
//
//			@Override
//			public RequestParams setParams(RequestParams requestParams) {
//				requestParams.add("action", "DelAliAccount");
//				requestParams.add("userid", GuangdaApplication.mUserInfo.getStudentid());
//				requestParams.add("type", "2");
//				return requestParams;
//			}
//
//			@Override
//			public void onFinish() {
//				mLoadingDialog.dismiss();
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, BindAliAccountResponse baseReponse) {
//				if (baseReponse != null) {
//					GuangdaApplication.mUserInfo.setAliaccount(null);
//					mAccountEt.setText("");
//				}
//				showToast("删除成功");
//			}
//
//		});
//	}

}
