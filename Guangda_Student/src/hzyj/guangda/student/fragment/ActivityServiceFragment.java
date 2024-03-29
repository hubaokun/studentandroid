package hzyj.guangda.student.fragment;

import org.apache.http.Header;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.views.LoadingDialog;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.ActivityService;
import hzyj.guangda.student.activity.BookDriveActivity;
import hzyj.guangda.student.activity.MapHomeActivity;
import hzyj.guangda.student.activity.SetLocationActivity;
import hzyj.guangda.student.activity.renrenStore;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.activity.order.FinishedFragment;
import hzyj.guangda.student.activity.personal.IdentityInfoActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetXiaoBaService;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.NeedCityDialog;

public class ActivityServiceFragment extends Fragment{
	 private View mBaseView;
	 private RelativeLayout rlBaoming;
     private LinearLayout rlZaixianYueKao;
     private LinearLayout rlMoniPeiXun;
     private RelativeLayout rlXiaoBaKeFu;
     private GuangdaApplication id;
     private ImageView back;
     private Context mcontext;
     private NeedCityDialog needCity;
     private String yuKaoUrl = "";
     private String PeiXunUrl = "";
     private TextView tv_location;
	 public  static String selectcityid ;
	 private String areaid="0";
	 private String provinceid="0";
	 private ImageView iv_menu;
	 public static String nowSelectCity;
	 public static String gpsCityId;
	 public LoadingDialog mLoadingDialog;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mcontext=getActivity();
		//needCity = new NeedCityDialog(mcontext);
		mBaseView = inflater.inflate(R.layout.activity_service,null);
		findViews();
		initLoadingDialog();
		addListeners();
		return mBaseView;
		
		
	}
	
	public void findViews() {
		// TODO Auto-generated method stub
		rlBaoming = (RelativeLayout)mBaseView.findViewById(R.id.rl_baoming);
		rlMoniPeiXun = (LinearLayout)mBaseView.findViewById(R.id.rl_monipeixun);
		rlZaixianYueKao = (LinearLayout)mBaseView.findViewById(R.id.rl_zaixianyukao);
		rlXiaoBaKeFu = (RelativeLayout)mBaseView.findViewById(R.id.rl_xiobakefu);
		id =(GuangdaApplication)getActivity().getApplication();
		
		back=(ImageView) mBaseView.findViewById(R.id.iv_back);
		tv_location=(TextView) mBaseView.findViewById(R.id.tv_location);
		iv_menu=(ImageView)mBaseView.findViewById(R.id.iv_menu);
		
	}
	
	private void initLoadingDialog() {
		mLoadingDialog = new LoadingDialog(mcontext);
		mLoadingDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				AsyncHttpClientUtil.get().cancelRequests(mcontext, true);
			}
		});
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getLocationCityUrl();
//		if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
//		{
//			cityid = GuangdaApplication.mUserInfo.getCityid();
//			areaid=GuangdaApplication.mUserInfo.getAreaid();
//			provinceid=GuangdaApplication.mUserInfo.getProvinceid();
//			
//			getXBservice();
//			
//			if(!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())){
//				String city = GuangdaApplication.mUserInfo.getCity().split("-")[1];
//				if(!TextUtils.isEmpty(city)){
//					tv_location.setText(city);
//				}
//				else{
//					tv_location.setText("");
//				}
//			}else{
//				if(!TextUtils.isEmpty(GuangdaApplication.location)){
//					tv_location.setText(GuangdaApplication.location);
//				}else{
//					tv_location.setText("");
//				}
//				
//			}
//			
////			cityId = mgr.queryCityName(city).cityid;
//		}else{
//			needCity.show();
//		}
		
		
		
		
	}
	public void addListeners() {
		// TODO Auto-generated method stub
		
		iv_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MapHomeActivity.mMenuIv.performClick();
				
			}
		});
		rlBaoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
//					//startMyActivity(LoginActivity.class);
//					 Intent intent = new Intent(mcontext,LoginActivity.class);
//		                startActivity(intent);
//				} else {
					//startMyActivity(BookDriveActivity.class);
//					Intent intent = new Intent(mcontext,BookDriveActivity.class);  //支付
//	                startActivity(intent);
	                
	                Intent intent = new Intent(mcontext,renrenStore.class);  //人人商城
	                startActivity(intent);
				
			}
		});
		
		rlZaixianYueKao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String url = "http://www.hzti.com:9004/drv_web/index.do";
//				
//				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
//				{
//					needCity.show();
//					return;
////					cityId = mgr.queryCityName(city).cityid;
//				}
				
				if (TextUtils.isEmpty(yuKaoUrl))
				{
					//showToast("该地区暂未收录");
					Toast.makeText(mcontext, "该地区暂未收录",Toast.LENGTH_SHORT);
					return;
				}
				 Uri u = Uri.parse(yuKaoUrl);  
				 Intent it = new Intent(Intent.ACTION_VIEW, u);
				 startActivity(it); 
			}
		});
		
		rlMoniPeiXun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String url = "http://xqc.qc5qc.com/reservation";
				
//				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
//				{
//					needCity.show();
//					return;
////					cityId = mgr.queryCityName(city).cityid;
//				}
				
				if (TextUtils.isEmpty(PeiXunUrl))
				{
					//showToast("该地区暂未收录");
					Toast.makeText(mcontext, "该地区暂未收录",Toast.LENGTH_SHORT);
					return;
				}
				 Uri u = Uri.parse(PeiXunUrl);  
				 Intent it = new Intent(Intent.ACTION_VIEW, u);
				 startActivity(it); 
			}
		});
		

		
		tv_location.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent (mcontext,IdentityInfoActivity.class);
				
				Intent intent = new Intent (mcontext,SetLocationActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("nowSelectCity", nowSelectCity);
				intent.putExtras(bundle);
				mcontext.startActivity(intent);
			}
		});
		

		rlXiaoBaKeFu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (mcontext,com.easemob.helpdeskdemo.activity.LoginActivity.class);
				 id.mUserInfo.getStudentid();
//				 System.out.println("____________点击我了__________________"+id.mUserInfo.getPhone()+GuangdaApplication.mUserInfo.getAvatarurl());
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
	
	private void getLocationCityUrl(){
		AsyncHttpClientUtil.get().post(mcontext, Setting.LOCATION_URL, GetXiaoBaService.class, new MySubResponseHandler<GetXiaoBaService>() {
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "getAddressUrl");
				requestParams.add("cityid", selectcityid);
				if(TextUtils.isEmpty(selectcityid)){
					requestParams.add("pointcenter",GuangdaApplication.mUserInfo.getLongitude() + "," + GuangdaApplication.mUserInfo.getLatitude());
				}
				
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
					if(!TextUtils.isEmpty(baseReponse.getCityname())){
						tv_location.setText(baseReponse.getCityname());
						//nowSelectCity=baseReponse.getCityname();
						nowSelectCity=baseReponse.getCityname();
					}if(!TextUtils.isEmpty(baseReponse.getCityid())){
						
					}
				}else{
					Toast.makeText(mcontext, baseReponse.getMessage(),Toast.LENGTH_LONG);
				}
			}
		});
	}
	
	private void getXBservice()
	{
		AsyncHttpClientUtil.get().post(mcontext, Setting.LOCATION_URL, GetXiaoBaService.class, new MySubResponseHandler<GetXiaoBaService>() {
			@Override
			public void onStart() {
				//mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETAUTOPOSITION");
				requestParams.add("cityid", selectcityid);
				requestParams.add("provinceid", provinceid);
				requestParams.add("areaid", areaid);
				//showToast(GuangdaApplication.mUserInfo.getStudentid().toString());
				return requestParams;
			}

			@Override
			public void onFinish() {
				//mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetXiaoBaService baseReponse) {
//				if (mLoadingDialog.isShowing())
//				{
//					mLoadingDialog.dismiss();
//				}
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
					//showToast(baseReponse.getMessage());
					Toast.makeText(mcontext, baseReponse.getMessage(),Toast.LENGTH_LONG);
				}
			}
		});
	}
}
