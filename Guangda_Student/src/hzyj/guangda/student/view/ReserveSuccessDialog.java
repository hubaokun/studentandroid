package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.order.MyOrderListActivity;
import hzyj.guangda.student.activity.order.OrderDetailActivity;
import hzyj.guangda.student.event.Update;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author liulj
 * 
 */
public class ReserveSuccessDialog extends BaseDialog {
	private ImageView mCloseIv;
	private TextView mReServeDetailTv;
	private String mOrderid;

	public ReserveSuccessDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public ReserveSuccessDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public ReserveSuccessDialog(Context context, String orderid) {
		super(context, R.style.dim_dialog);
		this.mOrderid = orderid;
	}

	public ReserveSuccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.reserve_success_dialog;
	}

	@Override
	protected void findViews() {
		mCloseIv = (ImageView) findViewById(R.id.iv_close);
		mReServeDetailTv = (TextView) findViewById(R.id.tv_reserve_detail);
		mReServeDetailTv.setSelected(true);
		mCloseIv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				EventBus.getDefault().post(new Update("SubjectReserveActivity"));
				((Activity) mContext).finish();
			}
		});
		// 订单详情
		mReServeDetailTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new Update("SubjectReserveActivity"));
				Intent intent = new Intent(mContext, MyOrderListActivity.class);
				intent.putExtra("mOrderid", mOrderid);
				mContext.startActivity(intent);
				dismiss();
				((Activity) mContext).finish();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setCancelable(false);
		setCanceledOnTouchOutside(false);
		setWindowParams(-1, -2, Gravity.CENTER);
	}

}
