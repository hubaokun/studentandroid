package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

/**
 * 意见反馈
 * 
 * @author liulj
 * 
 */
public class FeedBackActivity extends TitlebarActivity {
	private EditText mFeedBackEt;

	@Override
	public int getLayoutId() {
		return R.layout.feedback_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mFeedBackEt = (EditText) findViewById(R.id.et_back);

	}

	@Override
	public void addListeners() {
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mFeedBackEt.getText().toString().trim())) {
					showToast("请输入反馈内容");
					return;
				}
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SSET_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
					@Override
					public void onStart() {
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "Feedback");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						requestParams.add("content", mFeedBackEt.getText().toString().trim());
						requestParams.add("type", "2");
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
						showToast("提交成功");
						finish();
					}
				});
			}
		});
	}

	@Override
	public void initViews() {
		setCenterText("意见反馈");
		setRightText("提交", 10);
	}

	@Override
	public void requestOnCreate() {

	}

}
