package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetNoticesResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.os.Bundle;
import android.widget.TextView;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.loopj.android.http.RequestParams;

/**
 * 消息详情
 * 
 * @author liulj
 * 
 */
public class MessageDetailActivity extends TitlebarActivity {
	private TextView mDetailTv, mDetailDateTv;
	private String mDetailStr, mDateStr, mNoticeid;
	private int mReadState;

	@Override
	public void getIntentData() {
		super.getIntentData();
		mDetailStr = getIntent().getStringExtra("mDetailStr");
		mDateStr = getIntent().getStringExtra("mDateStr");
		mNoticeid = getIntent().getStringExtra("mNoticeid");
		mReadState =0;
	}

	@Override
	public int getLayoutId() {
		return R.layout.message_detail_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mDetailTv = (TextView) findViewById(R.id.tv_detail);
		mDetailDateTv = (TextView) findViewById(R.id.tv_msg_date);
	}

	@Override
	public void addListeners() {

	}

	@Override
	public void initViews() {
		setCenterText("消息详情");
		setText(mDetailTv, mDetailStr);
		String date = TimeUitlLj.millisecondsToString(9, TimeUitlLj.stringToMilliseconds(2, mDateStr));
		setText(mDetailDateTv, date);
	}

	@Override
	public void requestOnCreate() {
		
			// 设置已读
			AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SSET_URL, GetNoticesResponse.class, new MySubResponseHandler<GetNoticesResponse>() {
				@Override
				public void onStart() {
					super.onStart();
					mLoadingDialog.show();
				}

				@Override
				public RequestParams setParams(RequestParams requestParams) {
					requestParams.add("action", "ReadNotice");
					requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
					requestParams.add("noticeid", mNoticeid);
					return requestParams;
				}

				@Override
				public void onFinish() {
					mLoadingDialog.dismiss();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, GetNoticesResponse baseReponse) {

				}
			});
		}
	

}
