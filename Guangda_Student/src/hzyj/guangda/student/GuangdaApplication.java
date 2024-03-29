package hzyj.guangda.student;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hzyj.guangda.student.common.Constants;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.common.UserInfo;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.BookCoachReponse;
import hzyj.guangda.student.response.GetCity;
import hzyj.guangda.student.response.GetCity.City;
import hzyj.guangda.student.response.GetCity.Province;
import hzyj.guangda.student.response.GetCity.Zone;
import hzyj.guangda.student.response.LoginResponse;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.ReserveNotSuccessDialog;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import db.DBManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.lifecycle.ActivityLifecycleCallbacksCompat;
import com.common.library.llj.lifecycle.LifecycleDispatcher;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.PackageMangerUtilLj;
import com.easemob.chat.EMChat;
import com.easemob.helpdeskdemo.DemoHXSDKHelper;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.loopj.android.http.RequestParams;
//import com.pgyersdk.crash.PgyCrashManager;
//import com.pgyersdk.feedback.PgyFeedbackShakeManager;
//import com.pgyersdk.update.PgyUpdateManager;
import com.testin.agent.TestinAgent;

import de.greenrobot.event.EventBus;


/**
 * 
 * @author liulj
 * 
 */
public class GuangdaApplication extends BaseApplication {
	public static  UserInfo mUserInfo;
	private LocationClient mLocClient;
	private MyLocationListener mMyLocationListener;
	private int mNum;
	private boolean isCourent = false;
	private static int state = 0;
	public List<Province> provinceArray = new ArrayList<Province>();
	public List<City> cityArray = new ArrayList<City>();
	public List<Zone> areaArray = new ArrayList<Zone>();
	public static String baiduId;
	public static boolean isToBaoMing;
	public static String location;//当前定位城市
		
	
	
	public static String currentUserNick = "";
	public static DemoHXSDKHelper hxSDKHelper = new DemoHXSDKHelper();
	public static int isInvited;
	
     
	

	@Override
	public void onCreate() {
		super.onCreate();
		//
		SDKInitializer.initialize(this);
		//
		//PgyCrashManager.register(this, Setting.PGY_APPID);
		//
		mUserInfo = new UserInfo(this);
		// 注册全局activity的监听
		LifecycleDispatcher.registerActivityLifecycleCallbacks(this, new MyActivityLifecycleCallbacksCompat());
		//
		//initLocationClient();
		//
		//autoLogin();
		EMChat.getInstance().init(getApplicationContext());
		 
		/**
		 * debugMode == true 时为打开，sdk 会在log里输入调试信息
		 * @param debugMode
		 * 在做代码混淆的时候需要设置成false
		 */
		EMChat.getInstance().setDebugMode(true);//在做打包混淆时，要关闭debug模式，如果未被关闭，则会出现程序无法运行问题
		hxSDKHelper.onInit(getApplicationContext());
		
		TestinAgent.init(this);
		//TestinAgent.uploadException(getApplicationContext(), String message, Throwable throwable);
		
		TestinAgent.setUserInfo("862851265@qq.com");
		TestinAgent.leaveBreadcrumb("User tapped a button");
		TestinAgent.setLocalDebug(true);//设置为true，则在log中打印崩溃堆栈
		
	}

	/**
	 * 定位
	 */
	private void initLocationClient() {
		mLocClient = new LocationClient(this);
		LocationClientOption option = new LocationClientOption();
		// option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mMyLocationListener = new MyLocationListener();
		mLocClient.registerLocationListener(mMyLocationListener);
		if (!mLocClient.isStarted()) {
			mLocClient.start();
		}
	}

	public void uploadPushInfo() {
		AsyncHttpClientUtil.get().post(this, Setting.SYSTEM_URL, LoginResponse.class, new MySubResponseHandler<LoginResponse>() {
			@Override
			public void onStart() {
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "UpdatePushInfo");
				requestParams.add("userid", mUserInfo.getStudentid());
				requestParams.add("usertype", 2 + "");
				requestParams.add("devicetype", 2 + "");   //android 2 ios 1 
				requestParams.add("jpushid", PushManager.getInstance().getClientid(GuangdaApplication.this));
				return requestParams;
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, LoginResponse baseReponse) {
			}
		});

	}

//	public void autoLogin() {
//		GuangdaApplication.state++;
//		System.out.println(""+GuangdaApplication.state);
//		Log.e("state", ""+GuangdaApplication.state);
//		if (!TextUtils.isEmpty(mUserInfo.getPassword()))
//			AsyncHttpClientUtil.get().post(this, Setting.SUSER_URL, LoginResponse.class, new MySubResponseHandler<LoginResponse>() {
//
//				@Override
//				public RequestParams setParams(RequestParams requestParams) {
//					requestParams.add("action", "Login");
//					requestParams.add("phone", mUserInfo.getPhone());
//					requestParams.add("password", mUserInfo.getPassword());
//					return requestParams;
//				}
//
//				@Override
//				public void onSuccess(int statusCode, Header[] headers, LoginResponse baseReponse) {
//					if (baseReponse.getUserInfo() != null) {
//						if(baseReponse.getCode() == 1)
//						{
////							System.out.println("success");
////							Log.e("state","success");
//						}else{
////							System.out.println("false");
////							Log.e("state", "false");
//						}
//						//EventBus.getDefault().post(new Update("UserInfo"));
//					}
//					//uploadPushInfo();
//				}
//			});
//	}
	
	@SuppressWarnings("unchecked")
	public void setLocation()
	{
		 InputStream is;
		try {
			is = this.getAssets().open("xiaobasql.json");
	         byte [] buffer = new byte[is.available()] ;
	         is.read(buffer);
	         String json = new String(buffer,"utf-8");
	         GetCity getCity = new Gson().fromJson(json, GetCity.class);
	         provinceArray = getCity.getChina();	         
	         is.close();
//	            JSONObject obj;
//	            try {
//	                obj = new JSONObject(json);
//	                provinceArray = (List<Province>)obj.getJSONArray("china");
//	            } catch (JSONException e){
////	                Log.e(TAG, "There was an error packaging faq json", e);
//	            	e.printStackTrace();
//	            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            mLocClient.unRegisterLocationListener(mMyLocationListener);
            // map view 销毁后不在处理新接收的位置
            if (arg0 == null)
                return;
            if (mNum != 0) {
                return;
            }
            mNum++;
            final String province = arg0.getProvince();
            final String city = arg0.getCity();
            final String area = arg0.getDistrict();
            AsyncHttpClientUtil.get().post(getApplicationContext(), Setting.SYSTEM_URL,
                    BaseReponse.class, new MySubResponseHandler<BaseReponse>() {

                        @Override
                        public RequestParams setParams(RequestParams requestParams) {
                            requestParams.add("action", "UpdateUserLocation");
                            requestParams.add("openid", getDeviceId(getApplicationContext()));
                            requestParams.add("devicetype", "2");
                            requestParams.add("usertype", "2");
                            requestParams.add("appversion",
                                    PackageMangerUtilLj.getAppVersionName(getApplicationContext()));
                            requestParams.add("province", province);
                            requestParams.add("city", city);
                            requestParams.add("area", area);
                            return requestParams;
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                BaseReponse baseReponse) {}
                    });

        }
    }

	/**
	 * activity的全局监听
	 * 
	 * @author liulj
	 * 
	 */
	
	private void isRunningForeground (Activity activity)  
	{  
	    ActivityManager am = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);  
	    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;  
	    String currentPackageName = cn.getPackageName();  
	    if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName()))  
	    {  
	    	if (isCourent==false)
	    	{
	    		//PgyUpdateManager.register(activity, Setting.PGY_APPID);
	    	}
	    	isCourent = true;
	    }  
	    else{
	    	isCourent = false;
	    }
	   
	}
	
	private class MyActivityLifecycleCallbacksCompat implements ActivityLifecycleCallbacksCompat {

		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

		}

		@Override
		public void onActivityStarted(Activity activity) {

		}

		@Override
		public void onActivityResumed(Activity activity) { 
			
			//蒲公英摇一摇
			//PgyFeedbackShakeManager.register(activity, Constants.PGY_APPID);
			//PgyUpdateManager.register(activity, Setting.PGY_APPID);
			isRunningForeground(activity);
		}

		@Override
		public void onActivityPaused(Activity activity) {
			// 停止该页面的请求
			AsyncHttpClientUtil.get().cancelRequests(activity, true);
			//蒲公英摇一摇
			//PgyFeedbackShakeManager.unregister();
			isRunningForeground(activity);
		}

		@Override
		public void onActivityStopped(Activity activity) {
//			PgyUpdateManager.unregister();
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {

		}

	}
	
	public String getDeviceId(Context mContext) {

        StringBuilder deviceId = new StringBuilder();

        try {

            // wifi mac地址
            WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if (!isEmpty(wifiMac)) {
                deviceId.append("wifi");
                deviceId.append(wifiMac);

                return deviceId.toString();
            }

            // IMEI（imei）
            TelephonyManager tm =
                    (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);

                return deviceId.toString();
            }

            // 序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);

                return deviceId.toString();
            }
            return getUUID(mContext);
        } catch (Exception e) {
            // e.printStackTrace();
            return getUUID(mContext);
        }
    }

    private String getUUID(Context mContext) {
        String uuid = null;
        SharedPreferences mShare = mContext.getSharedPreferences("deviceid", mContext.MODE_PRIVATE);
        if (mShare != null) {
            uuid = mShare.getString("uuid", "");
        }
        if (!isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString("uuid", uuid);
            mShare.edit().commit();
        }
        return uuid;
    }

    private boolean isEmpty(String string ){
         if(string != null && string.length() > 0)
             return false;
         return true;
     }
    
    public String getVersion() {
    	try {
    	       PackageManager manager = this.getPackageManager();
    	       PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
    	       String version = info.versionName;
    	       return version;
    	   } catch (Exception e) {
    	       e.printStackTrace();
    	       return "";
    	     }
    	 }

}
