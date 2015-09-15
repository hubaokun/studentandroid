package hzyj.guangda.student.view;

import java.util.ArrayList;

import com.baidu.location.ad;
import com.common.library.llj.base.BaseDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.response.OpenCity;
import hzyj.guangda.student.response.getCoachHomeResponse;
import hzyj.guangda.student.response.getCoachHomeResponse.getInfor;
import hzyj.guangda.student.view.WheelCityPriceDialog.OnCityClickListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class WheelCoachHomeDialog extends BaseDialog{
	
	private WheelView mcitywheel;
	ArrayList<getCoachHomeResponse.getInfor> coachInfor;
	private OnCoachClickListener mOnComfirmClickListener;
	private EditText et_school;
	private String findSchool;
	private ArrayList<getCoachHomeResponse.getInfor> findcoschschool = new ArrayList<getCoachHomeResponse.getInfor>();
	private getCoachHomeResponse.getInfor getschool;
 
	public WheelCoachHomeDialog(Context context) {
		super(context, R.style.dim_dialog);
		// TODO Auto-generated constructor stub
	}
	
	public void WheelCityData( ArrayList<getCoachHomeResponse.getInfor> coachInfor) {
		this.coachInfor=coachInfor;
	}

	public WheelCoachHomeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public WheelCoachHomeDialog(Context context, int theme) {
		super(context, theme);
	}
	

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.filter_school_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		et_school=(EditText)findViewById(R.id.et_school);
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
				if (findcoschschool.size()!=0)
				{
				String schoolid = findcoschschool.get(mcitywheel.getCurrentItem()).getSchoolid();
				String name=findcoschschool.get(mcitywheel.getCurrentItem()).getName();
				String telphone=findcoschschool.get(mcitywheel.getCurrentItem()).getTelphone();
				
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onCoachBtnClick(schoolid,name,telphone);
				}
				dismiss();
			}
			}
		});
		
		et_school.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				findcoschschool.clear();
				if(!et_school.getText().toString().trim().equals("")){
				  findSchool=et_school.getText().toString();
					for(getInfor school:coachInfor){
						if(school.getName().contains(findSchool)){
							findcoschschool.add(school);
						}
			    	}
				}else{
					findcoschschool.addAll(coachInfor);
				}
				String[] ArraySchool = new String[findcoschschool.size()];
				for(int i=0;i<findcoschschool.size();i++){
					ArraySchool[i]=findcoschschool.get(i).getName();
		    	}
				setUpData(ArraySchool);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void setOnCoachClickListener(OnCoachClickListener l) {
		mOnComfirmClickListener = l;
	}
	
	public interface OnCoachClickListener {
		void onCoachBtnClick(String schoolid,String name,String telphone);
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
			et_school.setText("");
			findcoschschool.clear();
			
		}
	};
	
	private OnShowListener onShowListener = new OnShowListener() {
		
		@Override
		public void onShow(DialogInterface dialog) {
			// TODO Auto-generated method stub
			findcoschschool.addAll(coachInfor);
			String[] school = getscool();
			setUpData(school);
		}
	};
	private String[] getscool(){
		String[] ArraySchool=new  String[coachInfor.size()];
		for(int i=0;i<coachInfor.size();i++){
			ArraySchool[i]=coachInfor.get(i).getName();
    	}
		return ArraySchool;
	}
	
	private void setUpData(String[] arraySchool)
    {
		CountryAdapter adapter = new CountryAdapter(mContext, arraySchool);
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
