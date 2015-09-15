package hzyj.guangda.student.view;

import java.util.ArrayList;

import com.common.library.llj.base.BaseDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.response.Modelprice;
import hzyj.guangda.student.response.OpenCity;
import hzyj.guangda.student.view.WheelCityPriceDialog.OnCityClickListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class WheelCarTypeDialog extends BaseDialog{
	
	private WheelView mcitywheel;
	ArrayList<Modelprice> modelprice;
	private  String[] ArrayCityType;
	private OnCityCarClickListener mOnComfirmClickListener;
	

	public WheelCarTypeDialog(Context context) {
		super(context, R.style.dim_dialog);
		// TODO Auto-generated constructor stub
	}
	
	public void WheelCarData(ArrayList<Modelprice> modelprice) {
		this.modelprice=modelprice;
	}

	public WheelCarTypeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public WheelCarTypeDialog(Context context, int theme) {
		super(context, theme);
	}
	

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.wheel_city_price;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		mcitywheel=(WheelView)findViewById(R.id.wv_city);
		mcitywheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mcitywheel.setWheelBackground(android.R.color.transparent);
		mcitywheel.setWheelForeground(R.drawable.wheel_val);
		mcitywheel.setVisibleItems(5);
		
		
		findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				String country = countries[mProvinceWheel.getCurrentItem()];
//				String city = cities[mProvinceWheel.getCurrentItem()][mCityWheel.getCurrentItem()];
//				if (mOnComfirmClickListener != null) {
//					mOnComfirmClickListener.onComfirmBtnClick(country, city);
//				}
				
				String cartype = modelprice.get(mcitywheel.getCurrentItem()).getName();
				int Marketprice=modelprice.get(mcitywheel.getCurrentItem()).getMarketprice();
				int xiaobaprice=modelprice.get(mcitywheel.getCurrentItem()).getXiaobaprice();
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onCityCarBtnClick(cartype,Marketprice,xiaobaprice);
				}
				dismiss();
			}
		});
		
		

		
	}
	
	public void setOnCarClickListener(OnCityCarClickListener l) {
		mOnComfirmClickListener = l;
	}
	
	public interface OnCityCarClickListener {
		void onCityCarBtnClick(String cartype,int Marketprice,int xiaobaprice);
	}
	
	
	

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
		setOnDismissListener(onDismissListener);
		setOnShowListener(onShowListener);
		
	}
	
	private OnDismissListener  onDismissListener = new OnDismissListener() {
		
		@Override
		public void onDismiss(DialogInterface dialog) {
			// TODO Auto-generated method stub

		}
	};
	
	private OnShowListener onShowListener = new OnShowListener() {
		
		@Override
		public void onShow(DialogInterface dialog) {
			// TODO Auto-generated method stub
			getcity();
			setUpData();
			
		}
	};
	private void getcity(){
		ArrayCityType=new  String[modelprice.size()];
		for(int i=0;i<modelprice.size();i++){
			ArrayCityType[i]=modelprice.get(i).getName();
    	}
	}
	
	private void setUpData()
    {
		CountryAdapter adapter = new CountryAdapter(mContext, ArrayCityType);
		mcitywheel.setViewAdapter(adapter);
		mcitywheel.setCurrentItem(0);
      
        //getZone();
    }
	
	private class CountryAdapter extends ArrayWheelAdapter<String> {

		int currentGet;
		int currentSet;

		public CountryAdapter(Context context, String[] items) {
			super(context, items);
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

}

