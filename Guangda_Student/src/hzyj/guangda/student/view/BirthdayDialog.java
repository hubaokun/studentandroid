package hzyj.guangda.student.view;

import hzyj.guangda.student.R;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

public class BirthdayDialog extends BaseDialog {
	private WheelView mYearWheel, mMonthWheel, mDayWheel;
	private OnComfirmClickListener mOnComfirmClickListener;

	public BirthdayDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public BirthdayDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public BirthdayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.birthday_dialog_layout;
	}

	@Override
	protected void findViews() {

		mYearWheel = (WheelView) findViewById(R.id.year);
		mMonthWheel = (WheelView) findViewById(R.id.month);
		mDayWheel = (WheelView) findViewById(R.id.data);

		mYearWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mYearWheel.setWheelBackground(android.R.color.transparent);
		mYearWheel.setWheelForeground(R.drawable.wheel_val);
		mYearWheel.setVisibleItems(5);

		mMonthWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mMonthWheel.setWheelBackground(android.R.color.transparent);
		mMonthWheel.setWheelForeground(R.drawable.wheel_val);
		mMonthWheel.setVisibleItems(5);

		mDayWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mDayWheel.setWheelBackground(android.R.color.transparent);
		mDayWheel.setWheelForeground(R.drawable.wheel_val);
		mDayWheel.setVisibleItems(5);
		// 设置监听器
		WheelChangedListener wheelChangedListener = new WheelChangedListener();
		Calendar calendar = Calendar.getInstance();
		// 设置年
		final int curYear = calendar.get(Calendar.YEAR);
		// 设置显示1916-2015(显示近100年)
		mYearWheel.setViewAdapter(new DateNumericAdapter(mContext, curYear - 99, curYear, 99));
		// 设置中间位置的显示当前年
		mYearWheel.setCurrentItem(99);
		mYearWheel.addChangingListener(wheelChangedListener);
		// 设置月份
		final int curMonth = calendar.get(Calendar.MONTH);
		mMonthWheel.setViewAdapter(new DateNumericAdapter(mContext, 1, 12, curMonth));
		// 设置中间位置的显示当前月
		mMonthWheel.setCurrentItem(curMonth);
		mMonthWheel.addChangingListener(wheelChangedListener);
		// 设置日
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		mDayWheel.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		// 设置中间位置的显示当前日
		mDayWheel.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

		findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int year = curYear - 99 + mYearWheel.getCurrentItem();
				int month = mMonthWheel.getCurrentItem() + 1;
				int day = mDayWheel.getCurrentItem() + 1;
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onComfirmBtnClick(year, month, day);
				}
				dismiss();
			}
		});

	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

	class WheelChangedListener implements OnWheelChangedListener {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			updateDays(mYearWheel, mMonthWheel, mDayWheel);
		}
	}

	private void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		// 获取滑动后的年
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - year.getCurrentItem() + 99);
		// 获取滑动后的月
		calendar.set(Calendar.MONTH, month.getCurrentItem());
		// 获取滑动后那个月的天数
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置最小日和对应月的最大日，并设置current为当日
		day.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	private class DateNumericAdapter extends NumericWheelAdapter {
		int currentGet;
		int currentSet;

		public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
			super(context, minValue, maxValue);
			this.currentSet = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			// if (currentGet == currentSet) {
			// view.setTextColor(Color.parseColor("#17b3ec"));
			// }
			view.setTextColor(Color.WHITE);
			view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
			view.setTypeface(Typeface.SANS_SERIF);
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
		void onComfirmBtnClick(int year, int month, int day);
	}
}
