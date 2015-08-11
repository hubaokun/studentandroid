package hzyj.guangda.student.activity.order;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetComplaintReasonResponse;
import hzyj.guangda.student.response.GetComplaintReasonResponse.Reason;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.ReasonDialog;
import hzyj.guangda.student.view.ReasonDialog.OnComfirmClickListener;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TextUtilLj;
import com.loopj.android.http.RequestParams;

/**
 * 投诉界面
 * 
 * @author liulj
 * 
 */
public class ComplaintActivity extends TitlebarActivity {
	private LinearLayout mSelectLi;
	private TextView mReasonTv;
	private EditText mContentEt;

	private TextView mPlaceOrderTimeTv, mOrderCoachTv, mOrderTimeTv, mOrderAddressTv, mAllMoneyTv, mAllMoneyTagTv;
	private String mOrderid;
	private String mCreatTime;
	private String mOrderCoach;
	private String mOrderTime;
	private String mOrderAddress;
	private String mAllMoney;

	private List<Reason> reasonlist = new ArrayList<Reason>();
	private String mReasonId;

	private ReasonDialog mResDialog;

	@Override
	public void getIntentData() {
		super.getIntentData();
		Intent intent = getIntent();
		mOrderid = intent.getStringExtra("mOrderid");
		mCreatTime = intent.getStringExtra("mCreatTime");
		mOrderCoach = intent.getStringExtra("mOrderCoach");
		mOrderTime = intent.getStringExtra("mOrderTime");
		mOrderAddress = intent.getStringExtra("mOrderAddress");
		mAllMoney = intent.getStringExtra("mAllMoney");
	}

	@Override
	public int getLayoutId() {
		return R.layout.complaint_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mSelectLi = (LinearLayout) findViewById(R.id.li_select);
		mReasonTv = (TextView) findViewById(R.id.tv_reason);
		mContentEt = (EditText) findViewById(R.id.et_content);

		mPlaceOrderTimeTv = (TextView) findViewById(R.id.tv_place_order_time);
		mOrderCoachTv = (TextView) findViewById(R.id.tv_order_coach);
		mOrderTimeTv = (TextView) findViewById(R.id.tv_order_time);
		mOrderAddressTv = (TextView) findViewById(R.id.tv_order_address);
		mAllMoneyTv = (TextView) findViewById(R.id.tv_all_money);
		mAllMoneyTagTv = (TextView) findViewById(R.id.tv_all_money_tag);

		mAllMoneyTagTv.getLayoutParams().width = (int) TextUtilLj.getTextViewLength(mPlaceOrderTimeTv, "下单时间：");

	}

	@Override
	public void addListeners() {
		mSelectLi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (reasonlist.size() != 0) {
					mResDialog.show();
				}
			}
		});
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mContentEt.getText().toString().trim())) {
					showToast("请输入投诉内容！");
					return;
				}
				if (TextUtils.isEmpty(mReasonId)) {
					showToast("请选择投诉类型！");
					return;
				}
				doResquest();
			}
		});
	}

	@Override
	public void initViews() {
		mCommonTitlebar.getCenterTextView().setText("投诉");
		setRightText("提交", 10);

		setText(mPlaceOrderTimeTv, mCreatTime);
		setText(mOrderCoachTv, mOrderCoach);
		setText(mOrderTimeTv, mOrderTime);
		setText(mOrderAddressTv, mOrderAddress);
		setText(mAllMoneyTv, mAllMoney);

		mResDialog = new ReasonDialog(this);
		mResDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(String reason, String reaId) {
				mReasonId = reaId;
				mReasonTv.setText(reason);
			}
		});
	}

	@Override
	public void requestOnCreate() {
		// 获取投诉原因
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, GetComplaintReasonResponse.class, new MySubResponseHandler<GetComplaintReasonResponse>() {

			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetComplaintReason");
				requestParams.add("type", 2 + "");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetComplaintReasonResponse baseReponse) {
				if (baseReponse.getReasonlist() != null) {
					reasonlist.addAll(baseReponse.getReasonlist());
					mResDialog.updateData(baseReponse.getReasonlist());
				}
			}
		});
	}

	private void doResquest() {
		// 投诉
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {

			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "Complaint");
				requestParams.add("userid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("type", 2 + "");
				requestParams.add("orderid", mOrderid);
				requestParams.add("reason", mReasonId);
				requestParams.add("content", mContentEt.getText().toString().trim());
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
				showToast("投诉成功");
				finish();
			}
		});
	}
}
