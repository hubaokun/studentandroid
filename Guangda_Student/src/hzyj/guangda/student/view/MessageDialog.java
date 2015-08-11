package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

/**
 * 
 * @author liulj
 * 
 */
public class MessageDialog extends BaseDialog {
	private TextView mMessageTv, mSureTv;

	public MessageDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public MessageDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public MessageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.message_dialog;
	}

	@Override
	protected void findViews() {
		mMessageTv = (TextView) findViewById(R.id.tv_message);
		mSureTv = (TextView) findViewById(R.id.tv_sure);

		mSureTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setMessage(String message) {
		mMessageTv.setText(message);
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.CENTER);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
	}

}
