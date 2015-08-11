package hzyj.guangda.student.view;

import hzyj.guangda.student.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.Toast;

import com.common.library.llj.base.BaseDialog;

/**
 * 滚轮日期对话框
 * 
 * @author liulj
 * 
 */
public class WheelDateDialog extends BaseDialog {
	private WheelView mYearWheel, mMonthWheel, mDayWheel;
	private OnComfirmClickListener mOnComfirmClickListener;

	public WheelDateDialog(Context context) {
		super(context, R.style.dim_dialog);
	}

	public WheelDateDialog(Context context, int theme) {
		super(context, theme);
	}

	public WheelDateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.data_dialog;
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
		
		
		// 设置月份
		final int curMonth = calendar.get(Calendar.MONTH);
		if (curMonth<11)
		{
			final int curYear = calendar.get(Calendar.YEAR);
			// 设置显示2015-2114(显示近100年)
			// mYearWheel.setViewAdapter(new DateNumericAdapter(mContext, curYear, curYear + 99, curYear));
			mYearWheel.setViewAdapter(new DateNumericAdapter(mContext, curYear, curYear, curYear));
			mMonthWheel.setViewAdapter(new DateNumericAdapter(mContext, curMonth + 1, curMonth + 2, curMonth + 1));
		}else{
			final int curYear = calendar.get(Calendar.YEAR);
			// 设置显示2015-2114(显示近100年)
			// mYearWheel.setViewAdapter(new DateNumericAdapter(mContext, curYear, curYear + 99, curYear));
			mYearWheel.setViewAdapter(new DateNumericAdapter(mContext, curYear, curYear+1, curYear));
			mMonthWheel.setViewAdapter(new DateNumericAdapter(mContext, curMonth + 1, curMonth + 1, curMonth + 1));
		}
		// 设置中间位置的显示当前年
		mYearWheel.setCurrentItem(0);
		mYearWheel.addChangingListener(wheelChangedListener);
		// 设置中间位置的显示当前月
		mMonthWheel.setCurrentItem(0);
		mMonthWheel.addChangingListener(wheelChangedListener);
		// 设置日
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		mDayWheel.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		// 设置中间位置的显示当前日
		mDayWheel.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

		findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				int month = 0,year;
				 year = calendar.get(Calendar.YEAR);
				switch (mMonthWheel.getCurrentItem()) {
				case 0:
					 month = calendar.get(Calendar.MONTH)+1;
					break;
				case 1:
					 month = calendar.get(Calendar.MONTH)+2;
					 break;
				}
				int day = mDayWheel.getCurrentItem()+1;
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onComfirmBtnClick(year, month, day);
				}
			}
		});
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
		switch (year.getCurrentItem()) {
		case 0:
			calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
			final int curMonth = calendar.get(Calendar.MONTH);
			if (curMonth<11)
			{
				month.setViewAdapter(new DateNumericAdapter(mContext, curMonth + 1, curMonth + 2, curMonth + 1));
			}else{
				month.setViewAdapter(new DateNumericAdapter(mContext, curMonth + 1, curMonth + 1, curMonth + 1));
			}
			break;
		case 1:
			calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)+1);
			month.setViewAdapter(new DateNumericAdapter(mContext,1,1,1));
			break;
		}
/*		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - year.getCurrentItem() + 99);*/
		// 获取滑动后的月
		switch(month.getCurrentItem())
		{
		case 0:
			calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
			break;
		case 1:
			calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+1);
			break;
		}
/*		calendar.set(Calendar.MONTH, month.getCurrentItem() + 5);*/
		// 获取滑动后那个月的天数
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置最小日和对应月的最大日，并设置current为当日
		day.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		calendar.clear();
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