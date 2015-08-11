package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

public class GenderDialog extends BaseDialog {
	private TextView mGirlTv, mBoyTv;
	private OnComfirmClickListener mOnComfirmClickListener;

	public GenderDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public GenderDialog(Context context, int theme) {
		super(context, theme);
	}

	public GenderDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gender_dialog_layout;
	}

	@Override
	protected void findViews() {
		mGirlTv = (TextView) findViewById(R.id.tv_girl);
		mBoyTv = (TextView) findViewById(R.id.tv_boy);
		mGirlTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onComfirmBtnClick(mGirlTv.getText().toString().trim());
				}
			}
		});
		mBoyTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onComfirmBtnClick(mBoyTv.getText().toString().trim());
				}
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

	public void setOnComfirmClickListener(OnComfirmClickListener l) {
		mOnComfirmClickListener = l;
	}

	public interface OnComfirmClickListener {
		void onComfirmBtnClick(String gender);
	}
}
