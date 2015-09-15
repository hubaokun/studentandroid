package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetMyBalanceResponse;
import hzyj.guangda.student.response.GetMyBalanceResponse.Record;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.DensityUtils;
import com.common.library.llj.utils.ParseUtilLj;
import com.loopj.android.http.RequestParams;

/**
 * 我的账号
 * 
 * @author liulj
 * 
 */
public class MyAccountActivity extends TitlebarActivity {
	private TextView mMoneyTv, mFreezeMoneyTv;
	private ListView mHistoryLv;
	private TextView mSoonRechargeTv, mWithdrawalsTv;
	private RecordAdapter mRecordAdapter;

	@Override
	public int getLayoutId() {
		return R.layout.my_account_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mMoneyTv = (TextView) findViewById(R.id.tv_money);
		mFreezeMoneyTv = (TextView) findViewById(R.id.tv_freeze_money);

		mHistoryLv = (ListView) findViewById(R.id.lv_history);
		mSoonRechargeTv = (TextView) findViewById(R.id.tv_soonRecharge);
		mWithdrawalsTv = (TextView) findViewById(R.id.tv_withdrawals);
	}

	@Override
	public void addListeners() {
		mSoonRechargeTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMyActivity(RechargeActivity.class);
			}
		});
		mWithdrawalsTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getAliaccount())) {
					showToast("您还没有设置账户，请先设置！");
					startMyActivity(BandAliAcountActivity.class);
				} else {
					startMyActivity(WithdrawalsActivity.class);
				}
			} 
		});
		mCommonTitlebar.getRightTextView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMyActivity(BandAliAcountActivity.class);
			}
		});
	}

	@Override
	public void initViews() {
		setCenterText("账户");

		setRightText("账户管理", 10);

		mRecordAdapter = new RecordAdapter(this, R.layout.my_account_activity_item);
		mHistoryLv.setAdapter(mRecordAdapter);
	}

	@Override
	public void requestOnCreate() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 获取账户余额
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetMyBalanceResponse.class, new MySubResponseHandler<GetMyBalanceResponse>() {
			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetMyBalanceInfo");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetMyBalanceResponse baseReponse) {
				initData(baseReponse);
			}
		});
	}

	private void initData(GetMyBalanceResponse baseReponse) {

		setText(mMoneyTv, baseReponse.getBalance() + "");

		setText(mMoneyTv, (int) baseReponse.getBalance() + "");

		mFreezeMoneyTv.setText("");
		mFreezeMoneyTv.append("(冻结金额：");
		int sizeFmoney = DensityUtils.dp2px(this, 14);

//		if (baseReponse.getFmoney() == 0) {
//			mFreezeMoneyTv.setVisibility(View.GONE);
//		} else {
			mFreezeMoneyTv.setVisibility(View.VISIBLE);
			SpannableString fmoney = new SpannableString(baseReponse.getFmoney() + "");
			fmoney.setSpan(new AbsoluteSizeSpan(sizeFmoney), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			fmoney.setSpan(new ForegroundColorSpan(Color.parseColor("#f7645c")), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			mFreezeMoneyTv.append(fmoney);
			mFreezeMoneyTv.append("元)");
//		}

		if (baseReponse.getRecordlist() != null) {
			mRecordAdapter.replaceAll(baseReponse.getRecordlist());
		}

	}

	private class RecordAdapter extends QuickAdapter<Record> {

		public RecordAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, Record item, int position) {
			if (item != null) {
				// 流水状态
				if (item.getType() == 1) {
					helper.setText(R.id.tv_tag, "充值");
				} else if (item.getType() == 2) {
					helper.setText(R.id.tv_tag, "提现");
				} else if (item.getType() == 3) {
					helper.setText(R.id.tv_tag, "订单支付");
				}else if(item.getType()==4){
					helper.setText(R.id.tv_tag,"提现不通过");
				}
				//
				helper.setText(R.id.tv_date, item.getAddtime());
				//
				TextView monry = helper.getView(R.id.tv_money);
				if (item.getType() == 1||item.getType()==4) {
					monry.setText("+" + (int) item.getAmount()+"元");
					monry.setTextColor(Color.parseColor("#50cb8c"));
				} else {
					monry.setText("-" + (int) item.getAmount()+"元");
					monry.setTextColor(Color.parseColor("#f7645c"));
				}
			}
		}
	}
}
