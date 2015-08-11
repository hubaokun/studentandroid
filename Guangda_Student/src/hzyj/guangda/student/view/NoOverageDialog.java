package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.setting.RechargeActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

public class NoOverageDialog extends BaseDialog {
	private ImageView mCloseIv;
	private TextView mReServeDetailTv;
	private TextView tvRefreshMoney;

	public NoOverageDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public NoOverageDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public NoOverageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.no_overage_dialog;
	}

	public void setRefreshMoney(String refreshMoney)
	{
		tvRefreshMoney.setText(refreshMoney);
	}
	
	@Override
	protected void findViews() {
		mCloseIv = (ImageView) findViewById(R.id.iv_close);
		mReServeDetailTv = (TextView) findViewById(R.id.tv_reserve_detail);
		tvRefreshMoney = (TextView)findViewById(R.id.tv_refresh_money);
		mReServeDetailTv.setSelected(true);
		mCloseIv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();

			}
		});
		// 去充值
		mReServeDetailTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();

				mContext.startActivity(new Intent(mContext, RechargeActivity.class));
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.CENTER);
	}

}
