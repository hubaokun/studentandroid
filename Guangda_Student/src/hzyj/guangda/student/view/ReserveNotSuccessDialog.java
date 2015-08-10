package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.SubjectReserveActivity;
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

public class ReserveNotSuccessDialog extends BaseDialog {
	private ImageView mCloseIv;
	private TextView mReServeDetailTv;
	private TextView mNotSuccessMessage;
	private String message,btnmsg;

	public ReserveNotSuccessDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public ReserveNotSuccessDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public ReserveNotSuccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.reserve_not_success_dialog;
	}
	
	public void setMessage(String Messge,String BtnMsg)
	{
		message = Messge;
		btnmsg = BtnMsg;
		mNotSuccessMessage.setText(message);
		mReServeDetailTv.setText(btnmsg);
	}

	@Override
	protected void findViews() {
		mCloseIv = (ImageView) findViewById(R.id.iv_close);
		mReServeDetailTv = (TextView) findViewById(R.id.tv_reserve_detail);
		mNotSuccessMessage = (TextView)findViewById(R.id.tv_not_success_message);
		mReServeDetailTv.setSelected(true);
		mCloseIv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new Update("SubjectReserveActivity"));
				((Activity) mContext).finish();
			}
		});
		// 重新选择时间
		mReServeDetailTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new Update("SubjectReserveActivity"));
				mContext.startActivity(new Intent(mContext, SubjectReserveActivity.class));
				((Activity) mContext).finish();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.CENTER);
	}

}
