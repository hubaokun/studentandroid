package hzyj.guangda.student.util;


import hzyj.guangda.student.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DialogUtil {
	DialogConfirmListener mConfirmDialogListener;

	public DialogUtil() {
	}

	public DialogUtil(DialogConfirmListener mListener) {
		mConfirmDialogListener = mListener;
	}

	public void setmConfirmDialogListener(DialogConfirmListener mListener) {
		this.mConfirmDialogListener = mListener;
	}

	/**
	 * 弹出对话框显示
	 */
	public Dialog CallConfirmDialog(String str,String TVconfirm,String TVcancel,Context mContext, Dialog mServiceDialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		mServiceDialog = builder.create();
		mServiceDialog.show();
		mServiceDialog.setContentView(R.layout.exit_dialog);
		mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
		params.gravity = Gravity.CENTER;
		mServiceDialog.getWindow().setAttributes(params);
		mServiceDialog.setCanceledOnTouchOutside(true);

		TextView msg = (TextView) mServiceDialog.findViewById(R.id.input_dialog_msg);
		msg.setText(str);
		TextView ok = (TextView) mServiceDialog.findViewById(R.id.input_dialog_ok);
		TextView cancel = (TextView) mServiceDialog.findViewById(R.id.input_dialog_cancel);
		if (!"".equals(TVconfirm))
		{
			ok.setText(TVconfirm);
		}
		if (!"".equals(TVcancel))
		{
			cancel.setText(TVcancel);
		}
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mConfirmDialogListener != null)
					mConfirmDialogListener.doConfirm("");
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mConfirmDialogListener != null)
					mConfirmDialogListener.doCancel();
			}
		});

		return mServiceDialog;
	}
	
}
