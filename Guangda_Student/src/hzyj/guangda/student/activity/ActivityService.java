package hzyj.guangda.student.activity;

import org.apache.http.Header;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.LogUtilLj;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import db.DBManager;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.activity.personal.IdentityInfoActivity;
import hzyj.guangda.student.activity.personal.PersonalInfoActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetXiaoBaService;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.BookSuccessDialog;
import hzyj.guangda.student.view.NeedCityDialog;

public class ActivityService extends TitlebarActivity {
	
	private RelativeLayout rlBaoming;
	private LinearLayout rlZaixianYueKao;
	private LinearLayout rlMoniPeiXun;
	private RelativeLayout rlXiaoBaKeFu;
	private String yuKaoUrl = "";
	private String PeiXunUrl = "";
	private LocationClient mLocationClient;
//	private MyLocationListener myListener = new MyLocationListener();
	private String GPSlocation;
	private String cityName;
	 private DBManager mgr;
	 private NeedCityDialog needCity;
	 private String cityid = "0";
	 private String areaid="0";
	 private String provinceid="0";
	 private GuangdaApplication id;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_service;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rlBaoming = (RelativeLayout)findViewById(R.id.rl_baoming);
		rlMoniPeiXun = (LinearLayout)findViewById(R.id.rl_monipeixun);
		rlZaixianYueKao = (LinearLayout)findViewById(R.id.rl_zaixianyukao);
		rlXiaoBaKeFu = (RelativeLayout)findViewById(R.id.rl_xiobakefu);
		id =(GuangdaApplication)getApplication();
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		rlBaoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
					startMyActivity(LoginActivity.class);
				} else {
					startMyActivity(BookDriveActivity.class);
				}
			}
		});
		
		rlZaixianYueKao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String url = "http://www.hzti.com:9004/drv_web/index.do";
				
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
				{
					needCity.show();
					return;
//					cityId = mgr.queryCityName(city).cityid;
				}
				
				if (TextUtils.isEmpty(yuKaoUrl))
				{
					showToast("该地区暂未收录");
					return;
				}
				 Uri u = Uri.parse(yuKaoUrl);  
				 Intent it = new Intent(Intent.ACTION_VIEW, u);
				 ActivityService.this.startActivity(it); 
			}
		});
		
		rlMoniPeiXun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String url = "http://xqc.qc5qc.com/reservation";
				
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
				{
					needCity.show();
					return;
//					cityId = mgr.queryCityName(city).cityid;
				}
				
				if (TextUtils.isEmpty(PeiXunUrl))
				{
					showToast("该地区暂未收录");
					return;
				}
				 Uri u = Uri.parse(PeiXunUrl);  
				 Intent it = new Intent(Intent.ACTION_VIEW, u);
				 ActivityService.this.startActivity(it); 
			}
		});
		
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (mBaseFragmentActivity,IdentityInfoActivity.class);
				mBaseFragmentActivity.startActivity(intent);
			}
		});
		rlXiaoBaKeFu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (ActivityService.this,com.easemob.helpdeskdemo.activity.LoginActivity.class);
				 id.mUserInfo.getStudentid();
				 System.out.println("____________点击我了__________________"+id.mUserInfo.getPhone()+GuangdaApplication.mUserInfo.getAvatarurl());
				intent.putExtra("phone", id.mUserInfo.getPhone());
				if(GuangdaApplication.mUserInfo.getAvatarurl()!=null){
					intent.putExtra("iv", GuangdaApplication.mUserInfo.getAvatarurl());
				}else {
					intent.putExtra("iv", "");
				}
				startActivity(intent);
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("小巴服务");
		
	}

	@Override
	public void requestOnCreate() {
		
//		mLocationClient = new LocationClient(mBaseApplication);
//		mLocationClient.registerLocationListener(myListener);
//		mLocationClient.start();
//		setLocationOption();
//		initLocationClient();
		mgr = new DBManager(mBaseFragmentActivity);
		needCity = new NeedCityDialog(mBaseFragmentActivity);
		
		// TODO Auto-generated method stub
		
	}
	
//	public class MyLocationListener implements BDLocationListener
//	{
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			// TODO Auto-generated method stub
//			StringBuffer sb = new StringBuffer(256);
//			sb.append(location.getCity());
//			GPSlocation = sb.toString();
//			if ("null".equals(GPSlocation))
//			{
//				GPSlocation = "无法定位";
//			}
//			
//		}
//	}
//	
//	private void setLocationOption()
//	{
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);
//		option.setAddrType("all");
//		option.setScanSpan(5000);
//		mLocationClient.setLocOption(option);
//	}
//	
//	private void initLocationClient() {
//		LocationClient mLocClient = new LocationClient(this);
//		LocationClientOption option = new LocationClientOption();
//		// option.setOpenGps(true);// 打开gps
//		option.setCoorType("bd09ll"); // 设置坐标类型
//		option.setAddrType("all"); // 返回的定位结果包含地址信息
//		mLocClient.setLocOption(option);
//		mLocClient.registerLocationListener(new BDLocationListener() {
//
//			@Override
//			public void onReceiveLocation(BDLocation arg0) {
//				if (arg0 == null )
//				{
//					GPSlocation = "无法定位";
//				}else{
//				// 此处设置开发者获取到的方向信息，顺时针0-360
//				MyLocationData locData = new MyLocationData.Builder().accuracy(arg0.getRadius()).direction(100).latitude(arg0.getLatitude()).longitude(arg0.getLongitude()).build();
//				
//				// 保存经纬度
//				LatLng ll = new LatLng(arg0.getLatitude(), arg0.getLongitude());
//				MapStatus mMapStatus = new MapStatus.Builder().target(ll).build();
//				MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//					GPSlocation=arg0.getCity().replace("市","");
//				}
//				setRightText(GPSlocation, 10, R.color.text_green);
//			}
//		});
//		mLocClient.start();
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//自动更新
		//PgyUpdateManager.register(this, Setting.PGY_APPID);
		// 获取消息的数量
		if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
		{
			cityid = GuangdaApplication.mUserInfo.getCityid();
			areaid=GuangdaApplication.mUserInfo.getAreaid();
			provinceid=GuangdaApplication.mUserInfo.getProvinceid();
			
			getXBservice();
			
			String city = GuangdaApplication.mUserInfo.getCity().split("-")[1];
			setRightText(city, 10, R.color.text_green);
//			cityId = mgr.queryCityName(city).cityid;
		}else{
			needCity.show();
		}
	}
	
	private void getXBservice()
	{
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.LOCATION_URL, GetXiaoBaService.class, new MySubResponseHandler<GetXiaoBaService>() {
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETAUTOPOSITION");
				requestParams.add("cityid", cityid);
				requestParams.add("provinceid", provinceid);
				requestParams.add("areaid", areaid);
				//showToast(GuangdaApplication.mUserInfo.getStudentid().toString());
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetXiaoBaService baseReponse) {
				if (mLoadingDialog.isShowing())
				{
					mLoadingDialog.dismiss();
				}
				if (baseReponse.getCode()==1)
				{
					yuKaoUrl = baseReponse.getBookreceptionUrl();
					if(TextUtils.isEmpty(yuKaoUrl)){
						rlZaixianYueKao.setVisibility(View.GONE);
					}else{
						rlZaixianYueKao.setVisibility(View.VISIBLE);
					}
					PeiXunUrl = baseReponse.getSimulateUrl();
					if(TextUtils.isEmpty(PeiXunUrl)){
						rlMoniPeiXun.setVisibility(View.GONE);
					}else{
						rlMoniPeiXun.setVisibility(View.VISIBLE);
					}
					
				}else{
					showToast(baseReponse.getMessage());
				}
			}
		});
	}
	
//	@Override
//    public void onBackPressed() {
//       // TODO Auto-generated method stub
//        super.onBackPressed();       
//        this.finish();
//    }
}
