package hzyj.guangda.student.activity.login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.ActivityInputRecord;
import hzyj.guangda.student.activity.MapHomeActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.GetVerCodeResponse;
import hzyj.guangda.student.response.LoginResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.listener.OnMyTextWatcher;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.PhoneUtilLj;
import com.loopj.android.http.RequestParams;

import db.DBManager;
import de.greenrobot.event.EventBus;

/**
 * 登录
 * 
 * @author liulj
 * 
 */
public class LoginActivity extends BaseFragmentActivity implements OnClickListener {
	private ImageView mBackIv, mHeaderIv, mLineNameIv, mLineWordIv;// 返回,头像,昵称，密码
	private EditText mNameEt, mWordEt;
	private TextView mLoginTv, mRegistTv, mForgetWordTv, mQQLoginTv, mWeixinLoginTv, mWeiboLoginTv, mGetCodeTv;
	private long mill;
	private DBManager mgr;
	private Context context;
	private CountDownTimer timer = new CountDownTimer(60000, 1000) {
		@Override
		public void onTick(long millisUntilFinished) {
			mill = millisUntilFinished;
			mGetCodeTv.setText((millisUntilFinished / 1000) + "″后重获取");
		}

		@Override
		public void onFinish() {
			cancel();
			mGetCodeTv.setText("获取验证码");
			mGetCodeTv.setSelected(true);
		}
	};

	@Override
	public int getLayoutId() {
		return R.layout.login_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		context = LoginActivity.this;
		mGetCodeTv = (TextView) findViewById(R.id.tv_get_code);
		mBackIv = (ImageView) findViewById(R.id.iv_back);
		mHeaderIv = (ImageView) findViewById(R.id.iv_header);

		mNameEt = (EditText) findViewById(R.id.et_name);
		mWordEt = (EditText) findViewById(R.id.et_word);
		mLineNameIv = (ImageView) findViewById(R.id.iv_line_name);
		mLineWordIv = (ImageView) findViewById(R.id.iv_line_word);

		mLoginTv = (TextView) findViewById(R.id.tv_login);
		mRegistTv = (TextView) findViewById(R.id.tv_regist);
		mForgetWordTv = (TextView) findViewById(R.id.tv_forget_word);
		mQQLoginTv = (TextView) findViewById(R.id.tv_qq);
		mWeixinLoginTv = (TextView) findViewById(R.id.tv_weixin);
		mWeiboLoginTv = (TextView) findViewById(R.id.tv_weibo);
	}

	@Override
	public void addListeners() {
		mGetCodeTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mGetCodeTv.isSelected()) {
					if (TextUtils.isEmpty(mNameEt.getText().toString().trim())) {
						showToast("请输入手机号！");
						return;
					}
					if (!PhoneUtilLj.isMobile(mNameEt.getText().toString().trim())) {
						showToast("请输入正确的手机号码！");
						return;
					}
					timer.start();
					mGetCodeTv.setSelected(false);
					AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetVerCodeResponse.class, new MySubResponseHandler<GetVerCodeResponse>() {
						@Override
						public void onStart() {
							super.onStart();
							mLoadingDialog.show();
						}

						@Override
						public RequestParams setParams(RequestParams requestParams) {
							requestParams.add("action", "GetVerCode");
							requestParams.add("phone", mNameEt.getText().toString().trim());
							requestParams.add("type", "2");
							return requestParams;
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers, GetVerCodeResponse baseReponse) {
							showToast(baseReponse.getMessage());
							mGetCodeTv.setSelected(false);
						}

						@Override
						public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
							mGetCodeTv.setSelected(true);
						}

						@Override
						public void onFinish() {
							super.onFinish();
							mLoadingDialog.dismiss();
						}
					});
				}
			}
		});
		mNameEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mLineNameIv.setSelected(true);
				} else {
					mLineNameIv.setSelected(false);
				}
			}
		});
		mNameEt.addTextChangedListener(new OnMyTextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().length() == 11 && mill == 0) {
					mGetCodeTv.setSelected(true);
				} else {
					mGetCodeTv.setSelected(false);
				}
			}
		});
		mWordEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mLineWordIv.setSelected(true);
				} else {
					mLineWordIv.setSelected(false);
				}
			}
		});
		mBackIv.setOnClickListener(this);
		mLoginTv.setOnClickListener(this);
		mRegistTv.setOnClickListener(this);
		mForgetWordTv.setOnClickListener(this);
		mQQLoginTv.setOnClickListener(this);
		mWeixinLoginTv.setOnClickListener(this);
		mWeiboLoginTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_login:
			if (TextUtils.isEmpty(mNameEt.getText().toString().trim())) {
				showToast("请填写手机号码或用户名或昵称！");
				return;
			}
			if (TextUtils.isEmpty(mWordEt.getText().toString().trim())) {
				showToast("请填写密码！");
				return;
			}
			AsyncHttpClientUtil.get().post(this, Setting.SUSER_URL, LoginResponse.class, new MySubResponseHandler<LoginResponse>() {
				@Override
				public void onStart() {
					mLoadingDialog.show();
				}

				@Override
				public RequestParams setParams(RequestParams requestParams) {
					requestParams.add("action", "Login");
					requestParams.add("phone", mNameEt.getText().toString().trim());
					requestParams.add("password", mWordEt.getText().toString().trim());
					requestParams.add("version",((GuangdaApplication)mBaseApplication).getVersion());
					requestParams.add("devicetype","1");
					return requestParams;
				}

				@Override
				public void onFinish() {
					mLoadingDialog.dismiss();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, LoginResponse baseReponse) {
					showToast("登录成功");
					if (baseReponse.getUserInfo() != null) {
						GuangdaApplication.mUserInfo.saveUserInfo(baseReponse.getUserInfo());
						EventBus.getDefault().post(new Update("UserInfo"));
						if (GuangdaApplication.mUserInfo.getProvinceid()!=null)
						{
						mgr = new DBManager(context);
						String provincename = mgr.getProvince(GuangdaApplication.mUserInfo.getProvinceid());
						String cityname = mgr.getCity(GuangdaApplication.mUserInfo.getCityid());
						String areaname = mgr.getArea(GuangdaApplication.mUserInfo.getAreaid());
						String locationname = provincename+"-"+cityname+"-"+areaname;
						GuangdaApplication.mUserInfo.setCity(locationname);
					 //ArrayList<Province> provincelist = (ArrayList<Province>) mgr.queryProvince();
						mgr.closeDB();
						}
						
						GuangdaApplication.isInvited=baseReponse.getIsInvited();
						((GuangdaApplication) mBaseApplication).uploadPushInfo();
						if(GuangdaApplication.isInvited==1){
//							if(judgmentData(GuangdaApplication.mUserInfo.getAddtime())){
								//跳转到邀请码
								startMyActivity(ActivityInputRecord.class);
//							}
						}
						else{
							
							startMyActivity(MapHomeActivity.class);	
						}
					}
					
					//startMyActivity(MapHomeActivity.class);
					finish();
					
					
				}

				@Override
				public void onNotSuccess(Context context, int statusCode, Header[] headers, LoginResponse baseReponse) {
					super.onNotSuccess(context, statusCode, headers, baseReponse);
					if (baseReponse.getCode() == 2) {
						showToast(baseReponse.getMessage());
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

				}
			});
			break;
		case R.id.tv_regist:
			startMyActivity(RegistActivity.class);
			break;
		case R.id.tv_forget_word:
			startMyActivity(FindWordFirstActivity.class);
			break;
		case R.id.tv_qq:

			break;
		case R.id.tv_weixin:

			break;
		case R.id.tv_weibo:

			break;

		default:
			break;
		}
	}

	@Override
	public void initViews() {

	}

	@Override
	public void requestOnCreate() {

	}
	
	private static boolean judgmentData(String data1){
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-M-d HH:mm:ss");
	    Date regist = null;
		try {
			regist = simple.parse(data1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Date Today = new Date(); 
	    long cha=Today.getTime()-regist.getTime();
        if(cha<0){
        	 
            return false; 
   
          }
   
          double result = cha * 1.0 / (1000 * 60 * 60);
   
          if(result<=6){ 
   
               return true; 
   
          }else{ 
   
               return false; 
   
          } 
   
		
	}
}
