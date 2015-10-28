package hzyj.guangda.student.activity;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.R.drawable;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.activity.order.MyOrderListActivity;
import hzyj.guangda.student.activity.personal.UserInfoActivity;
import hzyj.guangda.student.activity.setting.MyAccountActivity;
import hzyj.guangda.student.activity.setting.MyCardActivity;
import hzyj.guangda.student.activity.setting.MyComplaitActivity;
import hzyj.guangda.student.activity.setting.SettingActivity;
import hzyj.guangda.student.activity.setting.SystemMessageActivity;
import hzyj.guangda.student.common.Constants;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.common.UserInfo;
import hzyj.guangda.student.entity.CoachInfoVo;
import hzyj.guangda.student.event.CoachFilterEvent;
import hzyj.guangda.student.event.CoachListEvent;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.CoachListResponse;
import hzyj.guangda.student.response.GetCarModelResponse;
import hzyj.guangda.student.response.GetCityId;
import hzyj.guangda.student.response.GetCarModelResponse.CarModel;
import hzyj.guangda.student.response.GetMessageCountResponse;
import hzyj.guangda.student.response.LoginResponse;
import hzyj.guangda.student.response.getAdvertiseResponse;
import hzyj.guangda.student.util.ImageUtils.OnImageLoad;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.MapBottomDialog;
import hzyj.guangda.student.view.MySlidingPaneLayout;
import hzyj.guangda.student.entity.Province;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import db.DBManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.DensityUtils;
import com.common.library.llj.utils.ImageUtils;
import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.MyResponseHandler;
import com.common.library.llj.utils.ViewHolderUtilLj;
import com.common.library.llj.views.LinearListView;
import com.common.library.llj.views.LoadingDialog;
import com.common.library.llj.views.UnscrollableGridView;
import com.loopj.android.http.RequestParams;
import com.pgyersdk.update.PgyUpdateManager;

import de.greenrobot.event.EventBus;
/**
 * 地图首页
 * 
 * @author liulj
 * 
 */
public class MapHomeActivity extends BaseFragmentActivity {
	private MySlidingPaneLayout mSlidingPaneLayout;
	private ImageView mHeaderIv, mMenuIv, mAllIv, mListIv, mFilterIv;
	private TextView mNameTv, mMobileTv;
	private UnscrollableGridView mMenuGv;
	private MenuGridAdapter mMenuGridAdapter;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private String mRadius;// 最大半径，单位米
	private BitmapDescriptor bitmapDescriptor;
	private BitmapDescriptor chosedBitmap;
	private BitmapDescriptor freeLearn;
	private BitmapDescriptor chosedFreeLearn;
	private float mZoom = 12.0f;
	private float mRotate = 0.0f;
	private double mLatitude;
	private double mLongitude;
	private int mDistanse;
	private boolean mMapLoaded;
	private MapBottomDialog mMapBottomDialog;
	private String nowlocaionId;

	private List<CoachInfoVo> mCoachInfoVos = new ArrayList<CoachInfoVo>();
	private boolean mHasMoreMsg;
	private String condition1;// 教练名字或车牌号
	private String condition3;// 时间刷选
	private String condition6 = "0";// 科目选择(0代表不限)
	public static String condition11;// // 车型
	private long mExitTime;
	private LinearListView mCardTypeLi;
	private List<CarModel> mCarModels = new ArrayList<CarModel>();
	private int count = 0,cityId;
	private CardTypeAdapter mCardTypeAdapter;
	private ImageView imgService,imgGps;
	 private DBManager mgr;
	 private LinearLayout nowLocation,ll_selector;
	
	private String city="";
	private Context context;
	private LinearLayout llHeader;
	private Marker mark;
	private LoadingDialog loadingdialog;
	private boolean reGps=false;   //是否重新定位
	private String type="2",model="2",height,width,url;
	private hzyj.guangda.student.view.ShowAdvertisementDialog showAdvDialog;
	private String android_flag;
	private String driverschoolid;
	private LinearLayout ll_waibiank;;
	@Override
	public int getLayoutId() {
		return R.layout.map_home_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		
		context = MapHomeActivity.this;
		mCardTypeLi = (LinearListView) findViewById(R.id.li_bottom_card_type);
		mSlidingPaneLayout = (MySlidingPaneLayout) findViewById(R.id.sliding_layout);
		initSlidingPaneLayout();
		mHeaderIv = (ImageView) findViewById(R.id.iv_head);
		mNameTv = (TextView) findViewById(R.id.tv_name);
		mMobileTv = (TextView) findViewById(R.id.tv_mobile);
		mMenuGv = (UnscrollableGridView) findViewById(R.id.gv_menu);
		mMenuIv = (ImageView) findViewById(R.id.iv_menu);
		mAllIv = (ImageView) findViewById(R.id.iv_all);
		mListIv = (ImageView) findViewById(R.id.iv_list);
		mFilterIv = (ImageView) findViewById(R.id.iv_filter);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		imgService = (ImageView)findViewById(R.id.img_service);
		llHeader = (LinearLayout)findViewById(R.id.li_menu_head);
		imgGps=(ImageView)findViewById(R.id.img_gps);
		ll_waibiank=(LinearLayout)findViewById(R.id.ll_waibiank);
		initFirstLocation();
		initLocationClient();
		autoLogin();
		((GuangdaApplication) mBaseApplication).setLocation();
		showAdvDialog = new hzyj.guangda.student.view.ShowAdvertisementDialog(mBaseFragmentActivity);
		getAdvertiseme();

	}
	
	private void getAdvertiseme(){
		
		WindowMetric();
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.ADVERTISE, getAdvertiseResponse.class, new MySubResponseHandler<getAdvertiseResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETADVERTISEMENT");
				requestParams.add("height",height);
				requestParams.add("width",width);
				requestParams.add("model",type);
				requestParams.add("type",type);
				requestParams.add("cityname",city);
				return requestParams;
			}

			@Override
			public void onFinish() {
				
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, getAdvertiseResponse baseReponse) {
				if(baseReponse.getCode()==1){
					android_flag=baseReponse.getS_flag();
					if(baseReponse.getS_flag().equals("2")){
						//showAdvDialog.setImageAdvertisement(baseReponse.getS_img_android());
						//loadImageDefault(baseReponse.getS_img_android(),400,400,showAdvDialog.imgAdvertisement);
						hzyj.guangda.student.util.ImageUtils.downloadAsyncTask(showAdvDialog.imgAdvertisement, baseReponse.getS_img_android());
						hzyj.guangda.student.util.ImageUtils.setImageShowListener(new OnImageLoad(){
							@Override
							public void showCancle(Boolean image) {
								// TODO Auto-generated method stub
								if(image){
									//showAdvDialog.imgClose.setBackgroundResource(drawable.img_close);
								}	
							}
							
						});
						showAdvDialog.show();
						url = baseReponse.getS_url();
					}
				}
			}
		});
		
	}
	
	
	private void WindowMetric(){
		 DisplayMetrics metric = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metric);
	         int a = metric.widthPixels;  // 屏幕宽度（像素）
	         int b = metric.heightPixels;  // 屏幕高度（像素）
	         width=String.valueOf(a);
	         height=String.valueOf(b);
	}

	private void initSlidingPaneLayout() {
		// 制造菜单显示的视差效果，要比菜单宽度小才有视察
		mSlidingPaneLayout.setParallaxDistance(200);
		// 设置显示菜单过程中主界面余下内容覆盖的渐变色，默认变灰色
		mSlidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
	}
	
	public void autoLogin() {
//		GuangdaApplication.state++;
//		System.out.println(""+GuangdaApplication.state);
//		Log.e("state", ""+GuangdaApplication.state);
		if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getPassword()))
			AsyncHttpClientUtil.get().post(this, Setting.SUSER_URL, LoginResponse.class, new MySubResponseHandler<LoginResponse>() {

				@Override
				public RequestParams setParams(RequestParams requestParams) {
					requestParams.add("action", "Login");
					requestParams.add("phone", GuangdaApplication.mUserInfo.getPhone());
					requestParams.add("password", GuangdaApplication.mUserInfo.getPassword());
					requestParams.add("version",((GuangdaApplication)mBaseApplication).getVersion());
					requestParams.add("devicetype","1");
					return requestParams;
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, LoginResponse baseReponse) {
					if (baseReponse.getUserInfo() != null) {
						if(baseReponse.getCode() == 1)
						{

							GuangdaApplication.mUserInfo.saveUserInfo(baseReponse.getUserInfo());
							
							GuangdaApplication.isInvited=baseReponse.getIsInvited();
							
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

//							if(GuangdaApplication.isInvited==1){
////								if(judgmentData(GuangdaApplication.mUserInfo.getAddtime())){
//									//跳转到邀请码
//									startMyActivity(ActivityInputRecord.class);
////								}
//							}
//							System.out.println("success");
//							Log.e("state","success");
						}else{
//							System.out.println("false");
//							Log.e("state", "false");
						}
						//EventBus.getDefault().post(new Update("UserInfo"));
					}
					//uploadPushInfo();
				}
			});
		
//		if(!nowlocaionId.equals(GuangdaApplication.baiduId)&&GuangdaApplication.baiduId!=null){
//		nowLocation.setVisibility(View.VISIBLE);
//	}
//	else{
//		nowLocation.setVisibility(View.GONE);
//	}
		
		
	}

	/**
	 * 计算地图在屏幕上显示的最大半径
	 */
	private void calculateMaxRadius() {
		LatLng startPoint = mBaiduMap.getProjection().fromScreenLocation(new Point(0, 0));
		LatLng endPoint = mBaiduMap.getProjection().fromScreenLocation(new Point(BaseApplication.DISPLAY_WIDTH, BaseApplication.DISPLAY_HEIGHT));
		mRadius = String.valueOf(DistanceUtil.getDistance(startPoint, endPoint));
	}

	/**
	 * 先设置一个初始地址
	 */
	private void initFirstLocation() {
		bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.map_car_img);
		chosedBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ico_carok);
		freeLearn=BitmapDescriptorFactory.fromResource(R.drawable.icon_closefree);
		chosedFreeLearn=BitmapDescriptorFactory.fromResource(R.drawable.ico_free);
		
		// 开启定位图层
		mBaiduMap = mMapView.getMap();
		// 跟随模式
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(LocationMode.FOLLOWING, true, null));
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				if (mSlidingPaneLayout.isOpen()) {
					mSlidingPaneLayout.closePane();
				}
				if (mMapBottomDialog.isShowing()) {
					mMapBottomDialog.dismiss();
				}
			}
		});
		mBaiduMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				mMapLoaded = true;
				LogUtilLj.LLJi("onMapLoaded" + mBaiduMap.getProjection());
				doRequest(0, false);
			}
		});
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				LogUtilLj.LLJi("onMapStatusChangeStart" + mBaiduMap.getProjection());
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				//
				LogUtilLj.LLJi("onMapStatusChangeFinish" + mBaiduMap.getProjection());
				if (arg0.zoom != mZoom || arg0.rotate != mRotate) {
					mZoom = arg0.zoom;
					mRotate = arg0.rotate;
					doRequest(0, true);
				}
			}

			@Override
			public void onMapStatusChange(MapStatus arg0) {
				LogUtilLj.LLJi("onMapStatusChange" + mBaiduMap.getProjection());
			}
		});
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				if (arg0 != null && arg0.getTitle() != null&&arg0.getPeriod()==1)
				{
					arg0.setIcon(chosedBitmap);
					mark = arg0;
					popBottomDialog(arg0.getTitle());
				}
				if(arg0 != null && arg0.getTitle() != null&&arg0.getPeriod()==2){
					arg0.setIcon(chosedFreeLearn);
					mark = arg0;
					popBottomDialog(arg0.getTitle());
				}
				
				return false;
			}
		});
		
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mMapView.removeViewAt(2);
		mMapView.removeViewAt(1);
		mMapView.showScaleControl(false);
		mBaiduMap.setMyLocationEnabled(true);
		LatLng center = null;
		if (GuangdaApplication.mUserInfo.getLatitude() != null && GuangdaApplication.mUserInfo.getLongitude() != null) {
			center = new LatLng(Double.valueOf(GuangdaApplication.mUserInfo.getLatitude()), Double.valueOf(GuangdaApplication.mUserInfo.getLongitude()));
		} else {
			center = new LatLng(30.249861, 120.18848);
		}
		MapStatus mMapStatus = new MapStatus.Builder().target(center).zoom(mZoom).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	/**
	 * 开启定位
	 */
	private void initLocationClient() {
		
		LocationClient mLocClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		// option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setAddrType("all"); // 返回的定位结果包含地址信息
		mLocClient.setLocOption(option);
		mLocClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation arg0) {
				if (arg0 == null || mMapView == null)
					return;
				// 此处设置开发者获取到的方向信息，顺时针0-360
				MyLocationData locData = new MyLocationData.Builder().accuracy(arg0.getRadius()).direction(100).latitude(arg0.getLatitude()).longitude(arg0.getLongitude()).build();
				mBaiduMap.setMyLocationData(locData);
				// 保存经纬度
				GuangdaApplication.mUserInfo.setLatitude(String.valueOf(arg0.getLatitude()));
				GuangdaApplication.mUserInfo.setLongitude(String.valueOf(arg0.getLongitude()));
				LatLng ll = new LatLng(arg0.getLatitude(), arg0.getLongitude());
				MapStatus mMapStatus = new MapStatus.Builder().target(ll).build();
				MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(mMapStatus);
				mBaiduMap.setMapStatus(u);
				if (mLatitude != arg0.getLatitude() && mLongitude != arg0.getLongitude()) {
					LatLng pre = new LatLng(mLatitude, mLongitude);
					LatLng next = new LatLng(arg0.getLatitude(), arg0.getLongitude());
					mDistanse = (int) DistanceUtil.getDistance(pre, next);
					if(arg0.getCity()!=null){
						city=arg0.getCity().replace("市","");
						GuangdaApplication.location=arg0.getCity().replace("市","");
					}
					nowlocaionId=arg0.getCityCode();
					if(!reGps){
						
						if(!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())){  //判断是否登录
							if (GuangdaApplication.mUserInfo.getCity() !=null)
							{
							if(GuangdaApplication.mUserInfo.getCity().indexOf(city)!=-1){
								//nowLocation.setVisibility(View.GONE);
						}
						else{
								showToast("您设置的驾考城市不是当前城市，可前往基本信息页面修改。");
							
						    }
							}else{
								showToast("请设置驾考城市");
							}
						}else{
							showToast("请登录");
						}
						
					}
					mLatitude = arg0.getLatitude();
					mLongitude = arg0.getLongitude();
					if(!city.equals("")){
						getCityId();
					}
				}
				if (mMapLoaded && (mDistanse > 100)) {
					LogUtilLj.LLJi("onReceiveLocation:" + "mDistanse" + mDistanse);
						doRequest(0, true);
				}
			}
		});
		mLocClient.start();
	}

	private void popBottomDialog(String coachId) {
		for (CoachInfoVo coachInfoVo : mCoachInfoVos) {
			if (coachInfoVo != null && (coachId.equals(coachInfoVo.getCoachid()))) {
				mMapBottomDialog.updateInfo(coachInfoVo);
				mMapBottomDialog.show();
				return;
			}
		}
	}

	@Override
	public void addListeners() {
		
		showAdvDialog.imgAdvertisement.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(android_flag.equals("1")){
					if(url!=null){
					if(!url.contains("http")){
						url="http://"+url;
						Uri u = Uri.parse(url);  
						Intent it = new Intent(Intent.ACTION_VIEW,u);
						MapHomeActivity.this.startActivity(it); 
					}		
				}
				}
				if(android_flag.equals("2")){
					if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
						GuangdaApplication.isToBaoMing=true;
						startMyActivity(LoginActivity.class);
					} else {
						startMyActivity(BookDriveActivity.class);
					}
				}
                if(android_flag.equals("0")){
                	return ;
                }
			}
		});
		
		
		llHeader.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
					startMyActivity(UserInfoActivity.class);
				} else {
					startMyActivity(LoginActivity.class);
				}
			}
		});
		
		imgGps.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onResume();
				
				reGps=true;
				initLocationClient();
				//getCardModelRequest();
				
			}
		});
		
//		nowLocation.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				nowLocation.setVisibility(View.GONE);
//			}
//		});
		
		mMenuIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getMessageCount();
				if (mSlidingPaneLayout.isOpen()) {
					mSlidingPaneLayout.closePane();
				} else {
					mSlidingPaneLayout.openPane();
				}
			}
		});
		mAllIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				condition1 = null;
				condition3 = null;
				condition6 = "0";
				driverschoolid = null;
				mCardTypeAdapter.notifyDataSetChanged();
				doRequest(1, false);
			}
		});
		mListIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mBaseFragmentActivity, CoachListActivity.class);
				intent.putExtra("condition1", condition1);
				intent.putExtra("condition3", condition3);
				intent.putExtra("condition6", condition6);
				intent.putExtra("condition11", condition11);
				intent.putExtra("driverschool", driverschoolid);
				intent.putExtra("mLatitude", String.valueOf(mLatitude));
				intent.putExtra("mLongitude",String.valueOf(mLongitude));
				intent.putExtra("cityId",String.valueOf(cityId));
				startActivity(intent);
			}
		});
		mFilterIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().postSticky(new CoachFilterEvent(condition1, condition3, condition6,driverschoolid));
				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mBaseFragmentActivity, R.anim.bottom_to_center, R.anim.no_fade);
				Intent intent = new Intent(mBaseFragmentActivity, CoachFilterActivity2.class);
				ActivityCompat.startActivity(mBaseFragmentActivity, intent, options.toBundle());
			}
		});
		
		imgService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
					startMyActivity(LoginActivity.class);
				} else {
					startMyActivity(ActivityService.class);
					}
			}
		});
		
	}
	
	
	
	public void getCityId() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.LOCATION_URL, GetCityId.class, new MySubResponseHandler<GetCityId>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETCITYBYCNAME");
				requestParams.add("cname",city);
				return requestParams;
			}

			@Override
			public void onFinish() {
				
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetCityId baseReponse) {
				if (baseReponse.getCitylist().size()!=0)
				{
					cityId=baseReponse.getCitylist().get(0).getCityid();
				}
				
			}
		});
	}

	@Override
	public void initViews() {
		// 是否现实全部按钮
		if (condition1 == null && condition3 == null && condition6.equals("0")&&driverschoolid == null) {
			mAllIv.setVisibility(View.INVISIBLE);
		} else {
			mAllIv.setVisibility(View.VISIBLE);
		}
		mMapBottomDialog = new MapBottomDialog(this);
		mMapBottomDialog.setCanceledOnTouchOutside(true); // dialog 点击外部消失
		mMapBottomDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub

				if (mark!=null&&mark.getPeriod()==1)
				{
					mark.setIcon(bitmapDescriptor);
					mark = null;
				}else{
					mark.setIcon(freeLearn);
					mark = null;
				}
			}
		});
		mMenuGridAdapter = new MenuGridAdapter(mBaseFragmentActivity, R.layout.map_home_activity_menu_item);
		mMenuGv.setAdapter(mMenuGridAdapter);
		MenuItem item1 = new MenuItem(R.drawable.menu_order_img, "我的订单");
		MenuItem item2 = new MenuItem(R.drawable.menu_account, "我的钱包");
//		MenuItem item3 = new MenuItem(R.drawable.book_drive, "一键报名");
//		MenuItem item4 = new MenuItem(R.drawable.book_test_online, "在线约考");
		MenuItem item5 = new MenuItem(R.drawable.menu_message, "系统消息");
		MenuItem item6 = new MenuItem(R.drawable.menu_setting, "设置");
		List<MenuItem> mMenuItems = new ArrayList<MenuItem>();
		mMenuItems.add(item1);
		mMenuItems.add(item2);
//		mMenuItems.add(item3);
//		mMenuItems.add(item4);
		mMenuItems.add(item5);
		mMenuItems.add(item6);
		mMenuGridAdapter.replaceAll(mMenuItems);
		mMenuGridAdapter.notifyDataSetChanged();
		mCardTypeAdapter = new CardTypeAdapter();
		mCardTypeLi.setAdapter(mCardTypeAdapter);
		initUserInfo(GuangdaApplication.mUserInfo);
		EventBus.getDefault().register(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//自动更新
		//PgyUpdateManager.register(this, Setting.PGY_APPID);
		// 获取消息的数量
		getMessageCount();
	}

	@Override
	protected void onStop() {
		super.onStop();
		//PgyUpdateManager.unregister();
	}

	public void onEventMainThread(Update update) {
		if (update.getType().equals("UserInfo")) {
			initUserInfo(GuangdaApplication.mUserInfo);
		}
	}

	/**
	 * 从筛选界面返回的数据
	 * 
	 * @param coachListEvent
	 */
	public void onEventMainThread(CoachListEvent coachListEvent) {
		if (coachListEvent != null) {
			condition1 = coachListEvent.getCondition1();
			condition3 = coachListEvent.getCondition3();
			condition6 = coachListEvent.getCondition6();
			driverschoolid = coachListEvent.getDriverschoolid();
			// 是否现实全部按钮
			if (condition1 == null && condition3 == null && condition6.equals("0") && driverschoolid == null) {
				mAllIv.setVisibility(View.INVISIBLE);
			} else {
				mAllIv.setVisibility(View.VISIBLE);
			}
			doRequest(2, false);
		}
	}

	private void initUserInfo(UserInfo userInfo) {
		// 头像图标
		loadHeadImage(userInfo.getAvatarurl(), 150, 150, mHeaderIv);
		// 姓名
		if (!TextUtils.isEmpty(userInfo.getRealname())) {
			mNameTv.setText(userInfo.getRealname());
		} else {
			if (!TextUtils.isEmpty(userInfo.getPhone())) {
				mNameTv.setText("未填写姓名");
			} else {
				mNameTv.setText("未登录");
			}
		}
		// 号码
		setText(mMobileTv, userInfo.getPhone());
	}

	@Override
	public void requestOnCreate() {
		// 获取车型
		getCardModelRequest();
	}

	/**
	 * 
	 * @param type
	 *            0默认不提示 1重置全部搜索2根据搜索条件搜索
	 * @param isUpateByStatus
	 *            是否是地图状态更新，是就不用设置loading
	 */
	private void doRequest(final int type, final boolean isUpateByStatus) {
		calculateMaxRadius();
		AsyncHttpClientUtil.get().post(this, Setting.SBOOK_URL, CoachListResponse.class, new MySubResponseHandler<CoachListResponse>() {
			@Override
			public void onStart() {
				if (!isUpateByStatus) {
					mLoadingDialog.show();
					mLoadingDialog.setOnDismissListener(new OnDismissListener() {

						@Override
						public void onDismiss(DialogInterface dialog) {
						}
					});
				}
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetNearByCoach");
				requestParams.add("pointcenter", GuangdaApplication.mUserInfo.getLongitude() + "," + GuangdaApplication.mUserInfo.getLatitude());
				requestParams.add("radius", mRadius);
				if (condition1 != null)
					requestParams.add("condition1", condition1);
				if (condition3 != null)
					requestParams.add("condition3", condition3 + " 05:00:00");
				if (condition6 != null)
					requestParams.add("condition6", condition6);
				if (condition11 != null)
					requestParams.add("condition11", condition11);
				if (driverschoolid != null)
				{
					requestParams.add("driverschoolid", driverschoolid);
				}
				if(GuangdaApplication.mUserInfo.getCityid()!=null){
					requestParams.add("cityid", GuangdaApplication.mUserInfo.getCityid()+"");
					//requestParams.add("cityname", GuangdaApplication.mUserInfo.getLocationname().split("-")[1]);
				}
				
				requestParams.add("version", ((GuangdaApplication) mBaseApplication).getVersion());
				if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid()))
				{
					requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				}
				//requestParams.add("cityid", cityId+"");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, CoachListResponse baseReponse) {
				if (type == 1) {
					//showToast("已经重置筛选");
					mAllIv.setVisibility(View.INVISIBLE);
				} else if (type == 2) {
					// if (baseReponse.getCoachlist() != null && baseReponse.getCoachlist().size() != 0) {
					//showToast("已根据筛选条件筛选出结果");
					// } else {
					// showToast("没有您想要的结果");
					// }
				}
				if (baseReponse.getCoachlist() != null) {
					mBaiduMap.clear();
					mCoachInfoVos.clear();
					mCoachInfoVos.addAll(baseReponse.getCoachlist());
					for (CoachInfoVo coachInfoVo : baseReponse.getCoachlist()) {
						if (coachInfoVo.getLongitude() != null && coachInfoVo.getLatitude() != null && coachInfoVo.getCoachid() != null&&coachInfoVo.getFreecoursestate()==0) {
							OverlayOptions overlayOptions = new MarkerOptions().position(new LatLng(Double.valueOf(coachInfoVo.getLatitude()), Double.valueOf(coachInfoVo.getLongitude())))
									.icon(bitmapDescriptor).title(coachInfoVo.getCoachid()).period(1); // 1  正常图标  2 免费体验课
							mBaiduMap.addOverlay(overlayOptions);
						}
						
						//免费体验图标
						if(coachInfoVo.getLongitude() != null && coachInfoVo.getLatitude() != null && coachInfoVo.getCoachid() != null&&coachInfoVo.getFreecoursestate()==1){
							OverlayOptions overlayOptions = new MarkerOptions().position(new LatLng(Double.valueOf(coachInfoVo.getLatitude()), Double.valueOf(coachInfoVo.getLongitude())))
									.icon(freeLearn).title(coachInfoVo.getCoachid()).period(2);
							mBaiduMap.addOverlay(overlayOptions);
						}
					}
					if(GuangdaApplication.mUserInfo.getLatitude()!=null&&GuangdaApplication.mUserInfo.getLongitude()!=null){
						LatLng ll = new LatLng(Double.valueOf(GuangdaApplication.mUserInfo.getLatitude()), Double.valueOf(GuangdaApplication.mUserInfo.getLongitude()));
						MapStatus mMapStatus = null;
						if (isUpateByStatus) {
							mMapStatus = new MapStatus.Builder().build();
						} else {
							mMapStatus = new MapStatus.Builder().target(ll).build();
						}
						MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
						mBaiduMap.setMapStatus(mMapStatusUpdate);
					}else{
						Toast.makeText(mBaseFragmentActivity, "未获取到您的坐标，请重新定位！", 0).show();
					}
					//LatLng ll = new LatLng(Double.valueOf(GuangdaApplication.mUserInfo.getLatitude()), Double.valueOf(GuangdaApplication.mUserInfo.getLongitude()));
				}

			}
		});
	}

	// 获取消息数量
	private void getMessageCount() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SSET_URL, GetMessageCountResponse.class, new MyResponseHandler<GetMessageCountResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetMessageCount");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				return requestParams;
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetMessageCountResponse baseReponse) {
				mHasMoreMsg = baseReponse.getNoticecount() > 0 ? true : false;
				mMenuGridAdapter.notifyDataSetChanged();
			}
		});

	}

	// 获得汽车型号
	private void getCardModelRequest() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.CUSER_URL, GetCarModelResponse.class, new MySubResponseHandler<GetCarModelResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetCarModel");
				requestParams.add("version", ((GuangdaApplication) mBaseApplication).getVersion());
				return requestParams;
			}
			
			@Override
			public void onNotSuccess(Context context, int statusCode, Header[] headers,
					GetCarModelResponse baseReponse) {
				// TODO Auto-generated method stub
				super.onNotSuccess(context, statusCode, headers, baseReponse);
				ll_waibiank.setVisibility(View.GONE);
				mCardTypeLi.setVisibility(View.GONE);
			}



			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				ll_waibiank.setVisibility(View.GONE);
				mCardTypeLi.setVisibility(View.GONE);
			}



			@Override
			public void onSuccess(int statusCode, Header[] headers, GetCarModelResponse baseReponse) {

				if (baseReponse.getModellist() != null && baseReponse.getModellist().size() != 0) {
					//ll_selector.setVisibility(View.VISIBLE);
					ll_waibiank.setVisibility(View.VISIBLE);
					mCardTypeLi.setVisibility(View.VISIBLE);
					if (GuangdaApplication.DISPLAY_WIDTH % DensityUtils.dp2px(mBaseFragmentActivity, 60) == 0) {
						count = GuangdaApplication.DISPLAY_WIDTH / DensityUtils.dp2px(mBaseFragmentActivity, 60);
					} else {
						count = GuangdaApplication.DISPLAY_WIDTH / DensityUtils.dp2px(mBaseFragmentActivity, 60);
					}
					mCarModels.clear();
					mCarModels.addAll(baseReponse.getModellist());
					if (mCarModels.size() != 0 && mCarModels.get(0) != null) {
						mCarModels.get(0).setSelect(true);
						condition11 = mCarModels.get(0).getModelid();
					}
					if (mMapLoaded) {
						doRequest(0, false);
					}
					mCardTypeAdapter.notifyDataSetChanged();
				} else {
					mCardTypeLi.setVisibility(View.GONE);
					ll_waibiank.setVisibility(View.GONE);
					//ll_selector.setVisibility(View.GONE);
				}
			}
		});

	}

	private class CardTypeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mCarModels.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.map_home_bottom_item, null);

			}
			final TextView textView = ViewHolderUtilLj.get(convertView, R.id.tv_bottom_item);
			final ImageView imageView = ViewHolderUtilLj.get(convertView, R.id.image);
			final LinearLayout wrap = ViewHolderUtilLj.get(convertView, R.id.li_wrap);
			if (mCarModels.size() <= count) {
				// 小于就等分
				wrap.getLayoutParams().width = (int) (((float)mCardTypeLi.getWidth()) / mCarModels.size());
			} else {
				// 大于就限制60dp
				wrap.getLayoutParams().width = DensityUtils.dp2px(mBaseFragmentActivity, 60);
			}
			final CarModel carModel = mCarModels.get(position);
			if (carModel != null) {
				if (carModel.isSelect()) {
					imageView.setSelected(true);
					textView.setSelected(true);
				} else {
					imageView.setSelected(false);
					textView.setSelected(false);
				}
				setText(textView, carModel.getSearchname());
				wrap.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (!textView.isSelected()) {
							for (CarModel carModel : mCarModels) {
								carModel.setSelect(false);
							}
							carModel.setSelect(true);
							imageView.setSelected(true);
							textView.setSelected(true);
							condition11 = carModel.getModelid();
							if(condition11.equals("19")){
								mFilterIv.setVisibility(View.GONE);
								mAllIv.setVisibility(View.GONE);
								condition1 = null;
								condition3 = null;
								condition6 = "0";
								driverschoolid = null;
							}else{
								mFilterIv.setVisibility(View.VISIBLE);
							}
							notifyDataSetChanged();
							doRequest(2, false);
						}
					}
				});
			}
			return convertView;
		}
	}

	private class MenuGridAdapter extends QuickAdapter<MenuItem> {

		public MenuGridAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, MenuItem item, final int position) {
			if (item != null) {
				helper.setImageResource(R.id.image, item.getRes());
				helper.setText(R.id.text, item.getName());
				
				//小红点 
				if (mHasMoreMsg && (position == 2)) {
					helper.setVisible(R.id.iv_msg_point, true);
				} else {
					helper.setVisible(R.id.iv_msg_point, false);
				}

				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						switch (position) {
						case 0:
							// 我的订单
							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
								startMyActivity(LoginActivity.class);
							} else {
								startMyActivity(MyOrderListActivity.class);
							}
							break;
						case 1:
							// 账户
							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
								startMyActivity(LoginActivity.class);
							} else {
								startMyActivity(MyAccount.class);
							}
							
							break;
						case 2:
							// 系统消息
//							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
//								startMyActivity(LoginActivity.class);
//							} else {
//								startMyActivity(BookDriveActivity.class);
//							}
							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
								startMyActivity(LoginActivity.class);
							} else {
								startMyActivity(SystemMessageActivity.class);
							}
							break;
						case 3:
							// 系统约考
							//startMyActivity(BookTestActivity.class);
							startMyActivity(SettingActivity.class);
							break;
//						case 4:
//							// 系统消息
//							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
//								startMyActivity(LoginActivity.class);
//							} else {
//								startMyActivity(SystemMessageActivity.class);
//							}
//							break;
//						case 5:
//							// 设置
//							startMyActivity(SettingActivity.class);
//							break;

						default:
							break;
						}

					}
				});
			}
		}
	}

	private class MenuItem {
		private int res;
		private String name;

		public MenuItem(int res, String name) {
			this.res = res;
			this.name = name;
		}

		public int getRes() {
			return res;
		}

		public void setRes(int res) {
			this.res = res;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	// 再次点击关闭页面
	@Override
	public void onBackPressed() {
		if (System.currentTimeMillis() - mExitTime > 2000) {
			mExitTime = System.currentTimeMillis();
			Toast.makeText(this, R.string.warn_exit_msg, Toast.LENGTH_SHORT).show();
		} else {
			mBaseApplication.finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
	public static boolean judgmentData(String data1){
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
