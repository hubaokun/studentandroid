package hzyj.guangda.student.activity;

import java.util.ArrayList;

import org.apache.http.Header;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.order.MyOrderListActivity;
import hzyj.guangda.student.activity.personal.IdentityInfoActivity;
import hzyj.guangda.student.alipay.AliPayTask;
import hzyj.guangda.student.alipay.Pparams;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.BookCoachReponse;
import hzyj.guangda.student.response.CarModelPriceResponse;
import hzyj.guangda.student.response.CarType;
import hzyj.guangda.student.response.CityIn;
import hzyj.guangda.student.response.GetCarCityPriceResponse;
import hzyj.guangda.student.response.GetCityId;
import hzyj.guangda.student.response.GetVerCodeResponse;
import hzyj.guangda.student.response.Modelprice;
import hzyj.guangda.student.response.OpenCity;
import hzyj.guangda.student.response.RechargeResponse;
import hzyj.guangda.student.response.getCarOrderInforResponse;
import hzyj.guangda.student.util.DialogConfirmListener;
import hzyj.guangda.student.util.DialogUtil;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.BookSuccessDialog;
import hzyj.guangda.student.view.NeedCityInfor;
import hzyj.guangda.student.view.ReserveNotSuccessDialog;
import hzyj.guangda.student.view.ReserveSuccessDialog;
import hzyj.guangda.student.view.WheelCarTypeDialog;
import hzyj.guangda.student.view.WheelCityDialog;
import hzyj.guangda.student.view.WheelCityPriceDialog;

import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TextUtilLj;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.R.bool;
import android.R.drawable;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BookDriveActivity extends BaseFragmentActivity {
	private ImageView btnBack;
	private TextView etName;
	private TextView tvPhone;
	private Button btnBookCoach;
	private TextView tvAuto;
	private TextView tvNeedOne;
	private TextView tvNeedTwo;
	private TextView tvNeedThreed;
	private Dialog mDialog=null;
	private BookDriveActivity mActivity;
	private LinearLayout ll_test_city;
	private LinearLayout ll_drive_type;
	private TextView tv_test_city,tv_test_car_type,tv_price,tv_normal_price,tv_pay;
	private ArrayList<OpenCity> cityInfor=new ArrayList<OpenCity>();
	private ArrayList<CarType> cartype=new ArrayList<CarType>();
	private ArrayList<Modelprice> modelprice=new ArrayList<Modelprice>();
	private WheelCityPriceDialog welcity;
	private WheelCarTypeDialog welCarType;
	private OpenCity city=new OpenCity();
	private boolean IsHave=false;
	private String sltCityid,sltCity,sltcartype,sltxiaobaprice,sltmarketprice;
	private boolean canPay;
	private LinearLayout ll_recrod,ll_price;
	private Button ll_pay_car_price;
	private TextView tv_name_cartype,money,tv_pay_time,tv_ispay;
	private RelativeLayout rl_market;
	private NeedCityInfor needcitynamedialog;
	private int cityposition=-1;
	private View vw_num;
	
	
	private boolean isCityUnInclude,IsPay;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.book_app_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		btnBack = (ImageView)findViewById(R.id.iv_map);
		//tvAuto = (TextView)findViewById(R.id.iv_filter);
		etName = (TextView)findViewById(R.id.et_book_name);
		tvPhone = (TextView)findViewById(R.id.et_book_phone);
		tv_test_city=(TextView)findViewById(R.id.tv_test_city);
		tv_test_car_type=(TextView)findViewById(R.id.tv_test_car_type);
		ll_test_city=(LinearLayout)findViewById(R.id.ll_test_city);
		ll_drive_type=(LinearLayout)findViewById(R.id.ll_drive_type);
		tv_price=(TextView)findViewById(R.id.tv_price);
		tv_normal_price=(TextView)findViewById(R.id.tv_normal_price);
		tv_pay=(TextView)findViewById(R.id.tv_pay);
		ll_pay_car_price=(Button)findViewById(R.id.ll_pay_car_price);
		ll_recrod=(LinearLayout)findViewById(R.id.ll_recrod);
		
		rl_market=(RelativeLayout)findViewById(R.id.rl_market);
		tv_name_cartype=(TextView)findViewById(R.id.tv_name_cartype);
		money=(TextView)findViewById(R.id.money);
		tv_pay_time=(TextView)findViewById(R.id.tv_pay_time);
		tv_ispay=(TextView)findViewById(R.id.tv_ispay);
		
		ll_price=(LinearLayout)findViewById(R.id.ll_price);
		vw_num=(View)findViewById(R.id.vw_num);
		
		needcitynamedialog=new NeedCityInfor(mBaseFragmentActivity);
		
		//btnBookCoach = (Button)findViewById(R.id.btn_book_drive);
		//tvNeedOne = (TextView)findViewById(R.id.tv_need_one);
		//tvNeedTwo = (TextView)findViewById(R.id.tv_need_two);
		//tvNeedThreed = (TextView)findViewById(R.id.tv_need_threed);
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getRealname())&&!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity()))
		{
				etName.setText(GuangdaApplication.mUserInfo.getRealname());	
				tv_test_city.setText(GuangdaApplication.mUserInfo.getCity().split("-")[1]);
				needcitynamedialog.dismiss();
		}
		else{
			needcitynamedialog.show();
//			DialogUtil dUtil = new DialogUtil(new DialogConfirmListener(){
//
//				@Override
//				public void doConfirm(String str) {
//					// TODO Auto-generated method stub
//					if(mDialog!=null){
//						Intent intent=new Intent(BookDriveActivity.this,IdentityInfoActivity.class);
//						startActivity(intent);
//						mDialog.dismiss();
//					}
//				}
//
//				@Override
//				public void doCancel() {
//					// TODO Auto-generated method stub
//					if(mDialog!=null){
//						mDialog.dismiss();
//					}
//				}	
//			});
			//mDialog=dUtil.CallConfirmDialog("填写学员基本信息","","",mActivity, mDialog);
		}
		requestOnCreate();
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		
		ll_test_city.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				welcity.WheelCityData(cityInfor);
				welcity.show();
			}
		});
		
		ll_drive_type.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isCityUnInclude){
					showToast("该城市未参加活动");
				}else{
					welCarType.WheelCarData(modelprice);
					welCarType.show();
				}
				
				
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		tv_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isCityUnInclude){
					tv_pay.setText("我要报名");
					orderByself();
					
				}else{
					payPrice();	
				}
				
			}
		});
		
//		tvAuto.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(BookDriveActivity.this,BookCoackActivity.class);
//				startActivity(intent);
//			}
//		});
		
//		btnBookCoach.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
//					@Override
//					public void onStart() {
//						mLoadingDialog.show();
//					}
//
//					@Override
//					public RequestParams setParams(RequestParams requestParams) {
//						requestParams.add("action", "ENROLL");
//						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
//						//showToast(GuangdaApplication.mUserInfo.getStudentid().toString());
//						return requestParams;
//					}
//
//					@Override
//					public void onFinish() {
//						mLoadingDialog.dismiss();
//					}
//
//					@Override
//					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
//						if (mLoadingDialog.isShowing())
//						{
//							mLoadingDialog.dismiss();
//						}
//						if (baseReponse.getCode()==1)
//						{
//							BookSuccessDialog bookSuccess = new BookSuccessDialog(mBaseFragmentActivity);
//							bookSuccess.setCanceledOnTouchOutside(true); // dialog 点击外部消失
//							bookSuccess.show();
//						}else{
//							showToast(baseReponse.getMessage());
//						}
//					}
//
//					@Override
//					public void onNotSuccess(Context context, int statusCode, Header[] headers, BaseReponse baseReponse) {
//						if (mLoadingDialog.isShowing())
//						{
//						mLoadingDialog.dismiss();
//						}
//						showToast(baseReponse.getMessage());
//						//new ReserveNotSuccessDialog(mBaseFragmentActivity).show();
//					}
//				});
//			}
//		});
	}
	
	private void orderByself(){
		
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
		@Override
		public void onStart() {
			mLoadingDialog.show();
		}

		@Override
		public RequestParams setParams(RequestParams requestParams) {
			requestParams.add("action", "ENROLL");
			requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
			//showToast(GuangdaApplication.mUserInfo.getStudentid().toString());
			return requestParams;
		}

		@Override
		public void onFinish() {
			mLoadingDialog.dismiss();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
			if (mLoadingDialog.isShowing())
			{
				mLoadingDialog.dismiss();
			}
			
			if (baseReponse.getCode()==1)
			{
				BookSuccessDialog bookSuccess = new BookSuccessDialog(mBaseFragmentActivity);
				bookSuccess.setCanceledOnTouchOutside(true); // dialog 点击外部消失
				bookSuccess.show();
			}else{
				showToast(baseReponse.getMessage());
			}
		}

		@Override
		public void onNotSuccess(Context context, int statusCode, Header[] headers, BaseReponse baseReponse) {
			if (mLoadingDialog.isShowing())
			{
			mLoadingDialog.dismiss();
			}
			showToast(baseReponse.getMessage());
			//new ReserveNotSuccessDialog(mBaseFragmentActivity).show();
		}
	});
		
	}
	
	
	private void payPrice(){
		
		Intent intent=new Intent(BookDriveActivity.this,ActivityRechargePay.class);
		Bundle bundle=new Bundle();
	    bundle.putString("money",sltxiaobaprice);
	    intent.putExtra("wPage","1");  //1 一键报名 2 充值 接口
	    intent.putExtra("cityid",sltCityid);
	    intent.putExtra("model", sltcartype);
	    
	    intent.putExtras(bundle);
	   
	    startActivity(intent);
		
		
//		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, RechargeResponse.class, new MySubResponseHandler<RechargeResponse>() {
//			@Override
//			public void onStart() {
//				super.onStart();
//				mLoadingDialog.show();
//			}
//
//			@Override
//			public RequestParams setParams(RequestParams requestParams) {
//				requestParams.add("action", "PROMOENROLL");
//				requestParams.add("studentid",GuangdaApplication.mUserInfo.getStudentid());
//				requestParams.add("cityid",sltCityid);
//				requestParams.add("model",sltcartype);
//				requestParams.add("amount",sltxiaobaprice);
//				return requestParams;
//			}
//
//			@Override
//			public void onFinish() {
//				mLoadingDialog.dismiss();
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, RechargeResponse baseReponse) {
//				Pparams mPparams = new Pparams();
//				mPparams.setBody(baseReponse.getBody());
//				mPparams.setNotify_url(baseReponse.getNotify_url());
//				mPparams.setOut_trade_no(baseReponse.getOut_trade_no());
//				mPparams.setPartner(baseReponse.getPartner());
//				mPparams.setRsakey(baseReponse.getPrivate_key());
//				mPparams.setSeller_id(baseReponse.getSeller_id());
//				mPparams.setSubject(baseReponse.getSubject());
//				mPparams.setTotal_fee(baseReponse.getTotal_fee());
//				// mPparams.setTotal_fee(0.01 + "");
//				AliPayTask mAliPayTask = new AliPayTask(mBaseFragmentActivity, mPparams, baseReponse.getOut_trade_no());
//				mAliPayTask.Execute();
//			}
//		});
//		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		mActivity = this;
		tvPhone.setText(GuangdaApplication.mUserInfo.getPhone());
		//tvNeedOne.setText(getResources().getString(R.string.need_one));
		//tvNeedTwo.setText(getResources().getString(R.string.need_two));
		//tvNeedThreed.setText(getResources().getString(R.string.need_threed));
		welcity = new WheelCityPriceDialog(this);
		welCarType=new WheelCarTypeDialog(this);
		welcity.setOnCityClickListener(new WheelCityPriceDialog.OnCityClickListener() {

			@Override
			public void onCityBtnClick(String city,String cityid) { 
				
				sltCityid = cityid;
				sltCity=city;
				tv_test_city.setText(sltCity);
				if(!sltCityid.equals("-1")){
					isCityUnInclude=false;
					tv_pay.setText("报名并支付");
					vw_num.setVisibility(View.VISIBLE);
					ll_drive_type.setVisibility(View.VISIBLE);
					ll_price.setVisibility(View.VISIBLE);
					ModelPrice();
				}else{
					isCityUnInclude=true;
					tv_pay.setText("我要报名");
					tv_test_car_type.setText("");
					tv_price.setText("0"+"元");
					tv_normal_price.setText("市场价:"+"0"+"元");
					vw_num.setVisibility(View.GONE);
					ll_drive_type.setVisibility(View.GONE);
					ll_price.setVisibility(View.GONE);
					
				}
			}
		});
		
		welCarType.setOnCarClickListener(new WheelCarTypeDialog.OnCityCarClickListener() {

			@Override
			public void onCityCarBtnClick(String cartype,int Marketprice,int xiaobaprice) { 
				
				sltcartype = cartype;
				sltmarketprice=Marketprice+"";
				sltxiaobaprice=xiaobaprice+"";
				
				tv_test_car_type.setText(sltcartype);
				tv_price.setText(sltxiaobaprice+"元");
				if(sltmarketprice.equals("0")){
					rl_market.setVisibility(View.GONE);
					tv_normal_price.setText("市场价:"+sltmarketprice+"元");
				}else{
					rl_market.setVisibility(View.VISIBLE);
					tv_normal_price.setText("市场价:"+sltmarketprice+"元");
				}
				
			}
		});
		
		
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		

		if(!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getRealname())&&!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getCity())){
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, GetCarCityPriceResponse.class, new MySubResponseHandler<GetCarCityPriceResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETOPENMODELPRICE");
				return requestParams;
			}

			@Override
			public void onFinish() {
				
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers, GetCarCityPriceResponse baseReponse) {
				if (baseReponse.getCode()==1)
				{
					for(int i=0;i<baseReponse.getOpencity().size();i++){
						if(GuangdaApplication.mUserInfo.getCity().split("-")[1].equals(baseReponse.getOpencity().get(i).getCityname())){
							cityInfor.clear();
							IsHave=true;
							cityInfor.addAll(baseReponse.getOpencity());
							isCityUnInclude=false;
							sltCityid=baseReponse.getOpencity().get(i).getCityid();
							cityposition=i-1;
							vw_num.setVisibility(View.VISIBLE);
							ll_drive_type.setVisibility(View.VISIBLE);
							ll_price.setVisibility(View.VISIBLE);
							ModelPrice();
						}
					}
					if(!IsHave){
						isCityUnInclude=true;
						tv_pay.setText("我要报名");
						cityInfor.clear();
						city.setCityname(GuangdaApplication.mUserInfo.getCity().split("-")[1]);
						vw_num.setVisibility(View.GONE);
						ll_drive_type.setVisibility(View.GONE);
						ll_price.setVisibility(View.GONE);
						city.setCityid("-1");
						cityInfor.add(city);
						cityInfor.addAll(baseReponse.getOpencity());
					}	
				}
			}
		});
		
		//是否支付
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, getCarOrderInforResponse.class, new MySubResponseHandler<getCarOrderInforResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETENROLLINFO");
				requestParams.add("studentid",GuangdaApplication.mUserInfo.getStudentid());
				return requestParams;
			}

			@Override
			public void onFinish() {
				
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers, getCarOrderInforResponse baseReponse) {
				if (baseReponse.getCode()==1)
				{
					if (baseReponse.getEnrollpay()!=null)
					{
					if(baseReponse.getEnrollstate().equals("1")&&(baseReponse.getEnrollpay().equals("-1")||baseReponse.getEnrollpay().equals("1"))){
						ll_pay_car_price.setVisibility(View.VISIBLE);
						tv_pay.setText("已报名");
						tv_test_city.setText(baseReponse.getCityname());
						tv_pay.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_had_pay));
						if(baseReponse.getEnrollpay().equals("1")){
							tv_test_car_type.setText(baseReponse.getModel());
							tv_price.setText(baseReponse.getXiaobaprice());
							if(baseReponse.getMarketprice().equals("0")){
								rl_market.setVisibility(View.GONE);
								tv_normal_price.setText("市场价"+baseReponse.getMarketprice()+"元");
							}else{
								rl_market.setVisibility(View.VISIBLE);
								tv_normal_price.setText("市场价"+baseReponse.getMarketprice()+"元");
							}
							if(baseReponse.getModel().equals("c1")){
								tv_name_cartype.setText(GuangdaApplication.mUserInfo.getRealname()+"-"+baseReponse.getModel()+"手动档");
							}else{
								tv_name_cartype.setText(GuangdaApplication.mUserInfo.getRealname()+"-"+baseReponse.getModel()+"自动挡");
							}
							money.setText(baseReponse.getXiaobaprice()+"元");
							tv_pay_time.setText(baseReponse.getEnrolltime());
							tv_ispay.setText("已支付");
							ll_recrod.setVisibility(View.VISIBLE);
						}
					}
					}
					else{
						ll_pay_car_price.setVisibility(View.GONE);
						ll_recrod.setVisibility(View.GONE);
					}
					
				}
			}
		});
		}
	}
	
	private void ModelPrice(){
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, CarModelPriceResponse.class, new MySubResponseHandler<CarModelPriceResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETMODELPRICE");
				requestParams.add("cityid",sltCityid);
				return requestParams;
			}

			@Override
			public void onFinish() {
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers, CarModelPriceResponse baseReponse) {
				if (baseReponse.getCode()==1)
				{
						sltcartype = baseReponse.getModelprice().get(0).getName();
						sltxiaobaprice=baseReponse.getModelprice().get(0).getXiaobaprice()+"";
						tv_test_car_type.setText(baseReponse.getModelprice().get(0).getName());
						tv_price.setText(baseReponse.getModelprice().get(0).getXiaobaprice()+""+"元");
						if(baseReponse.getModelprice().get(0).getMarketprice()==0){
							rl_market.setVisibility(View.GONE);
						}else{
							rl_market.setVisibility(View.VISIBLE);
							tv_normal_price.setText("市场价"+baseReponse.getModelprice().get(0).getMarketprice()+""+"元");
						}
					
					
					modelprice.clear();
					modelprice.addAll(baseReponse.getModelprice());
				}
			}
		});
	}

}
