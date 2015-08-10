package hzyj.guangda.student.view;

import java.util.ArrayList;
import java.util.List;

import hzyj.guangda.student.R;
import hzyj.guangda.student.response.GetComplaintReasonResponse.Reason;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

/**
 * 
 * @author liulj
 * 
 */
public class ReasonDialog extends BaseDialog {
	private WheelView mWheelView;
	private OnComfirmClickListener mOnComfirmClickListener;
	private List<Reason> mReasonlist;

	public ReasonDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public ReasonDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public ReasonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.reason_dialog_layout;
	}

	@Override
	protected void findViews() {
		mWheelView = (WheelView) findViewById(R.id.wv_reason);
		mWheelView.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mWheelView.setWheelBackground(android.R.color.transparent);
		mWheelView.setWheelForeground(R.drawable.wheel_val);
		mWheelView.setVisibleItems(5);
		mWheelView.setCyclic(false);

		findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnComfirmClickListener != null) {
					if (mReasonlist.get(mWheelView.getCurrentItem()) != null)
						mOnComfirmClickListener.onComfirmBtnClick(mReasonlist.get(mWheelView.getCurrentItem()).getContent(), mReasonlist.get(mWheelView.getCurrentItem()).getSetid() + "");
				}
				dismiss();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

	/**
	 * 
	 * @param item
	 */
	public void updateData(List<Reason> reasonlist) {
		mReasonlist = reasonlist;
		mWheelView.setViewAdapter(new CountryAdapter(mContext, mReasonlist.toArray(new Reason[] {}), 1));
	}

	private class CountryAdapter extends ArrayWheelAdapter<Reason> {
		int currentGet;
		int currentSet;

		public CountryAdapter(Context context, Reason[] items, int current) {
			super(context, items);
			this.currentSet = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTextColor(Color.WHITE);
			view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public CharSequence getItemText(int index) {
			Reason reason = mReasonlist.get(index);
			if (reason != null) {
				return reason.getContent();
			}
			return null;
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentGet = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	public void setOnComfirmClickListener(OnComfirmClickListener l) {
		mOnComfirmClickListener = l;
	}

	public interface OnComfirmClickListener {
		void onComfirmBtnClick(String reason, String reaId);
	}
}
