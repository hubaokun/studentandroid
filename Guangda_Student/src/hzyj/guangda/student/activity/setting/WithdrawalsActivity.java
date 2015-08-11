package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.ParseUtilLj;
import com.loopj.android.http.RequestParams;

/**
 * 提现界面
 * 
 * @author liulj
 * 
 */
public class WithdrawalsActivity extends TitlebarActivity {
	private EditText mMoneyEt;

	@Override
	public int getLayoutId() {
		return R.layout.withdrawals_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mMoneyEt = (EditText) findViewById(R.id.et_money);

	}

	@Override
	public void addListeners() {
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mMoneyEt.getText().toString().trim())) {
					showToast("请输入金额");
					return;
				}
				if (ParseUtilLj.parseInt(mMoneyEt.getText().toString().trim()) <= 0) {
					showToast("请输入正确的金额");
					return;
				}
				if(ParseUtilLj.parseInt(mMoneyEt.getText().toString().trim())<=10){
					showToast("提现金额大于10元");
					return;
				}
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
					@Override
					public void onStart() {
						super.onStart();
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "ApplyCash");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						requestParams.add("count", mMoneyEt.getText().toString().trim());
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
						showToast("提现成功");
						finish();
					}

					@Override
					public void onNotSuccess(Context context, int statusCode, Header[] headers, BaseReponse baseReponse) {
						super.onNotSuccess(context, statusCode, headers, baseReponse);
						if (baseReponse.getCode() == 3) {
							showToast("余额不足");
						}else{
							showToast(baseReponse.getMessage());
						}
					}
				});
			}
		});
	}

	@Override
	public void initViews() {
		setCenterText("请输入提现金额");
		setRightText("提交", 10);

	}

	@Override
	public void requestOnCreate() {

	}

}
