package hzyj.guangda.student.activity.personal;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.WheelCityDialog;

import org.apache.http.Header;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.PhoneUtilLj;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 基本信息修改
 * 
 * @author liulj
 * 
 */
public class IdentityInfoActivity extends TitlebarActivity {
	private EditText mMobileEt, mRealNameEt;
	private TextView mCityTv;
	private WheelCityDialog mWheelCityDialog;
	private String provinceId,cityId,zoneId,baiduId;

	@Override
	public int getLayoutId() {
		return R.layout.identity_info_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mMobileEt = (EditText) findViewById(R.id.et_mobile);
		mRealNameEt = (EditText) findViewById(R.id.et_real_name);
		mCityTv = (TextView) findViewById(R.id.tv_city);
	}

	@Override
	public void addListeners() {
		mMobileEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mMobileEt.setSelected(true);
				} else {
					mMobileEt.setSelected(false);
				}
			}
		});
		mRealNameEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mRealNameEt.setSelected(true);
				} else {
					mRealNameEt.setSelected(false);
				}
			}
		});
		
		mCityTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mWheelCityDialog.show();
			}
		});
		
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mMobileEt.getText().toString().trim())) {
					showToast("请输入手机号码！");
					return;
				}
				if (!PhoneUtilLj.isMobile(mMobileEt.getText().toString().trim())) {
					showToast("请输入正确的手机号码！");
					return;
				}
				if (TextUtils.isEmpty(mRealNameEt.getText().toString().trim())) {
					showToast("请输入真实姓名！");
					return;
				}
				if(TextUtils.isEmpty(provinceId)||TextUtils.isEmpty(cityId)||TextUtils.isEmpty(zoneId)){
					showToast("请选择城市！");
					return;
				}
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
					@Override
					public void onStart() {
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "PerfectAccountInfo");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						requestParams.add("realname", mRealNameEt.getText().toString().trim());
						requestParams.add("phone", mMobileEt.getText().toString().trim());
						requestParams.add("provinceid",provinceId);
						requestParams.add("cityid", cityId);
						requestParams.add("areaid", zoneId);
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
						showToast("修改成功！");

						GuangdaApplication.mUserInfo.setPhone(mMobileEt.getText().toString().trim());
						GuangdaApplication.mUserInfo.setRealname(mRealNameEt.getText().toString().trim());

						EventBus.getDefault().post(new Update("UserInfo"));
						GuangdaApplication.mUserInfo.setCity(mCityTv.getText().toString().trim());
						GuangdaApplication.mUserInfo.setCityid(cityId);
						GuangdaApplication.mUserInfo.setProvinceid(provinceId);
						GuangdaApplication.mUserInfo.setAreaid(zoneId);
						GuangdaApplication.mUserInfo.setBaiduid(baiduId);
						finish();
					}

					@Override
					public void onNotSuccess(Context context, int statusCode, Header[] headers, BaseReponse baseReponse) {
						super.onNotSuccess(context, statusCode, headers, baseReponse);
						if (baseReponse.getCode() == 2) {
							showToast("用户不存在");
						} else if (baseReponse.getCode() == 3) {
							showToast("电话号码已经被占用");
						}
					}
				});
			}
		});
	}

	@Override
	public void initViews() {
		mCommonTitlebar.getCenterTextView().setText("学员基本信息");
		mCommonTitlebar.getRightTextView().setText("提交");
		// 手机号码
		setText(mMobileEt, GuangdaApplication.mUserInfo.getPhone());
		// 真实名字
		if (GuangdaApplication.mUserInfo.getRealname() != null) {
			setText(mRealNameEt, GuangdaApplication.mUserInfo.getRealname());
		}
		
		mWheelCityDialog = new WheelCityDialog(this);
		mWheelCityDialog.setOnComfirmClickListener(new WheelCityDialog.OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(String province, String city,String zone,String provinceid,String cityid,String zoneid,String baiduid) { 
				mCityTv.setText(province + "-" + city+"-"+zone);
				mCityTv.setTextColor(Color.parseColor("#252525"));
				provinceId = provinceid;
				cityId = cityid;
				zoneId = zoneid;
				baiduId = baiduid;
			}
		});
	}

	@Override
	public void requestOnCreate() {
		setText(mCityTv, GuangdaApplication.mUserInfo.getCity());
		cityId = GuangdaApplication.mUserInfo.getCityid();
		provinceId = GuangdaApplication.mUserInfo.getProvinceid();
		zoneId = GuangdaApplication.mUserInfo.getAreaid();
	}

}
