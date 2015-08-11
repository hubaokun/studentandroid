package hzyj.guangda.student.activity.personal;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.BirthdayDialog;
import hzyj.guangda.student.view.BirthdayDialog.OnComfirmClickListener;
import hzyj.guangda.student.view.GenderDialog;
import hzyj.guangda.student.view.WheelCityDialog;

import org.apache.http.Header;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

/**
 * 个人信息
 * 
 * @author liulj
 * 
 */
public class PersonalInfoActivity extends TitlebarActivity {
/*	private EditText mAddressEt, mSoonNameEt, mSoonNumEt;*/
	private TextView mGenderTv, mBirthTv; //mCityTv;
	private BirthdayDialog mBirthdayDialog;

	private GenderDialog mGenderDialog;
	private String provinceId,cityId,zoneId;
	boolean hasCity;

	@Override
	public int getLayoutId() {
		return R.layout.personal_info_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mGenderTv = (TextView) findViewById(R.id.tv_gender);
		mBirthTv = (TextView) findViewById(R.id.tv_birth);

//		mAddressEt = (EditText) findViewById(R.id.et_address);
//		mSoonNameEt = (EditText) findViewById(R.id.et_soon_name);
//		mSoonNumEt = (EditText) findViewById(R.id.et_soon_num);
	}

	@Override
	public void addListeners() {
		mGenderTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mGenderDialog.show();
			}
		});
		mBirthTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mBirthdayDialog.show();
			}
		});
//		mCityTv.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				mWheelCityDialog.show();
//			}
//		});
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
					@Override
					public void onStart() {
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "PerfectPersonInfo");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						if ("男".equals(mGenderTv.getText().toString().trim())) {
							requestParams.add("gender", 1 + "");
						} else {
							requestParams.add("gender", 2 + "");
						}
						requestParams.add("birthday", mBirthTv.getText().toString().trim());
//						requestParams.add("city", mCityTv.getText().toString().trim());
//						requestParams.add("address", mAddressEt.getText().toString().trim());
//						requestParams.add("urgentperson", mSoonNameEt.getText().toString().trim());
//						requestParams.add("urgentphone", mSoonNumEt.getText().toString().trim());
//						requestParams.add("provinceid",provinceId);
//						requestParams.add("cityid", cityId);
//						requestParams.add("areaid", zoneId);
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
						showToast("修改成功！");
						if ("男".equals(mGenderTv.getText().toString().trim())) {
							GuangdaApplication.mUserInfo.setGender(1);
						} else if ("女".equals(mGenderTv.getText().toString().trim())) {
							GuangdaApplication.mUserInfo.setGender(2);
						} else {
							GuangdaApplication.mUserInfo.setGender(0);
						}
						GuangdaApplication.mUserInfo.setBirthday(mBirthTv.getText().toString().trim());

//						GuangdaApplication.mUserInfo.setCity(mCityTv.getText().toString().trim());
//						GuangdaApplication.mUserInfo.setCityid(cityId);
//						GuangdaApplication.mUserInfo.setProvinceid(provinceId);
//						GuangdaApplication.mUserInfo.setAreaid(zoneId);
//						GuangdaApplication.mUserInfo.setAddress(mAddressEt.getText().toString().trim());
//						GuangdaApplication.mUserInfo.setUrgent_person(mSoonNameEt.getText().toString().trim());
//						GuangdaApplication.mUserInfo.setUrgent_phone(mSoonNumEt.getText().toString().trim());
						finish();
					}

					@Override
					public void onNotSuccess(Context context, int statusCode, Header[] headers, BaseReponse baseReponse) {
						super.onNotSuccess(context, statusCode, headers, baseReponse);
						if (baseReponse.getCode() == 2) {
							showToast("用户不存在");
						}
					}
				});
			}
		});
	}

	@Override
	public void initViews() {
		mBirthdayDialog = new BirthdayDialog(this);
		mBirthdayDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(int year, int month, int day) {
				String monthStr = month < 10 ? "0" + month : "" + month;
				String dayStr = day < 10 ? "0" + day : "" + day;
				mBirthTv.setText(year + "-" + monthStr + "-" + dayStr);
			}
		});
		
		
//		mWheelCityDialog = new WheelCityDialog(this);
//		mWheelCityDialog.setOnComfirmClickListener(new WheelCityDialog.OnComfirmClickListener() {
//
//			@Override
//			public void onComfirmBtnClick(String province, String city,String zone,String provinceid,String cityid,String zoneid,String baiduid) { 
//				mCityTv.setText(province + "-" + city+"-"+zone);
//				mCityTv.setTextColor(Color.parseColor("#252525"));
//				provinceId = provinceid;
//				cityId = cityid;
//				zoneId = zoneid;
//			}
//		});
		
//		mWheelCityDialog.setOnComfirmClickListener(new WheelCityDialog.OnComfirmClickListener() {
//
//			@Override
//			public void onComfirmBtnClick(String province, String city,String zone,String provinceid,String cityid,String zoneid) {
//				mCityTv.setText(province + " " + city+"	"+zone);
//				mCityTv.setTextColor(Color.parseColor("#252525"));
//				hasCity = true;
//				provinceId = provinceid;
//				cityId = cityid;
//				zoneId = zoneid;
//				//setClickable();
//			}
//		});
		mGenderDialog = new GenderDialog(this);
		mGenderDialog.setOnComfirmClickListener(new GenderDialog.OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(String gender) {
				mGenderDialog.dismiss();
				mGenderTv.setText(gender);
			}
		});
		//
		mCommonTitlebar.getCenterTextView().setText("个人信息");
		//
		mCommonTitlebar.getRightTextView().setText("提交");
	}

	@Override
	public void requestOnCreate() {
		initInfo();
	}

	private void initInfo() {
//		showToast(GuangdaApplication.mUserInfo.getGender()+"");
		if (GuangdaApplication.mUserInfo.getGender() == 1) {
			mGenderTv.setText("男");
		} else if (GuangdaApplication.mUserInfo.getGender() == 2) {
			mGenderTv.setText("女");
		} else {
			mGenderTv.setText("保密");
		}
		setText(mBirthTv, GuangdaApplication.mUserInfo.getBirthday());
//		setText(mCityTv, GuangdaApplication.mUserInfo.getCity());
//		cityId = GuangdaApplication.mUserInfo.getCityid();
//		provinceId = GuangdaApplication.mUserInfo.getProvinceid();
//		zoneId = GuangdaApplication.mUserInfo.getAreaid();
//		setText(mAddressEt, GuangdaApplication.mUserInfo.getAddress());
//		setText(mSoonNameEt, GuangdaApplication.mUserInfo.getUrgent_person());
//		setText(mSoonNumEt, GuangdaApplication.mUserInfo.getUrgent_phone());
	}
}
