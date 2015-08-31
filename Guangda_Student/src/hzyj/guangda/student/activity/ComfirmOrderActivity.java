package hzyj.guangda.student.activity;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.setting.RechargeActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.entity.Coupon;
import hzyj.guangda.student.entity.Request;
import hzyj.guangda.student.event.ComfirmOrderActivityEvent;
import hzyj.guangda.student.response.BookCoachReponse;
import hzyj.guangda.student.response.UserMoneyResponse;
import hzyj.guangda.student.response.CoachScheduleResponse.Data;
import hzyj.guangda.student.response.GetCanUseCouponResponse;

import hzyj.guangda.student.response.OrderList;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.ChosePayWayDialog;
import hzyj.guangda.student.view.NoOverageDialog;
import hzyj.guangda.student.view.ReserveNotSuccessDialog;
import hzyj.guangda.student.view.ReserveSuccessDialog;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.OnCheckedChanged;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TextUtilLj;
import com.common.library.llj.utils.TimeUitlLj;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 订单列表，确认订单界面
 * 
 * @author liulj
 * 
 */
@SuppressLint("InflateParams")
public class ComfirmOrderActivity extends TitlebarActivity {
	private LinearLayout mContentLi;
	private TextView mAllMoneyTv, mSureOrderTv, mHasUseTv;
	private float mAllMoney, mOverage;// 订单的总额，没有使用优惠券
	private float mHasUseCouponMoney;// 使用学时券的总额
	private String mCoachId;// 教练的id
	private List<Request> mRequestData = new ArrayList<Request>();
	private List<Coupon> mCouponList = new ArrayList<Coupon>();
	private List<Coupon> mUseCouponList = new ArrayList<Coupon>();
	private LongSparseArray<List<Data>> mRemain;// 上一个页面传入的所有的订单数据，需要进行先按日期进行升序，然后每个list中按日期中的小时进行升序，排好后再进行分割（日期分割，小时没有连在一起的分割）
	private GetCanUseCouponResponse mGetCanUseCouponResponse;
	private long keyIndex = 0;// 订单的index(时间连在一起的算一个订单,订单从keyIndex=1开始)
	private NoOverageDialog mNoOverageDialog;
	private ChosePayWayDialog chosePayWay;
	private float mHasUseCoinsNum = 0; //使用小巴币的总额
	private float mHasUseResetMoney = 0;
	private float canUseResetMoney = 0; //可使用余额的总额
	private float canUseCoinsNum = 0;  //可是用小巴币的总额 
	private List<OrderList> hasChosedOrderPrice = new ArrayList<OrderList>();
	private int isFirstLoad = 0; //判断是否是第一次进入这个页面，0是第一次，1不是第一次 
	private int chosedIndex;
	private TextView tvHasCoin;
	private TextView tvHasRest;
	private float hasCoin = 0;
	private float hasMoney = 0;
	private boolean hasmoneyflag = true;
	private float mixCoins=0; //混合支付 小巴币支付的个数
	private boolean IspayQuan=false;
	

	@Override
	public void getIntentData() {
		super.getIntentData();
		mCoachId = getIntent().getStringExtra("mCoachId");
		mOverage = getIntent().getFloatExtra("mOverage", 0);
		chosePayWay = new ChosePayWayDialog(mBaseFragmentActivity);
	}

	@Override
	public int getLayoutId() {
		return R.layout.comfirm_order_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mContentLi = (LinearLayout) findViewById(R.id.li_content);
		mAllMoneyTv = (TextView) findViewById(R.id.tv_all_money);
		mHasUseTv = (TextView) findViewById(R.id.tv_has_use);
		mSureOrderTv = (TextView) findViewById(R.id.tv_sure);
		tvHasCoin = (TextView) findViewById(R.id.tv_use_coin);
		tvHasRest = (TextView)findViewById(R.id.tv_use_rest);
	}

	@Override
	public void addListeners() {
		
		//预定流程
		mSureOrderTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (hasmoneyflag == false)
				{
					startActivity(new Intent(ComfirmOrderActivity.this, RechargeActivity.class));
				}else{
				if (mSureOrderTv.isSelected()) {
					if (canUseResetMoney-mHasUseResetMoney<0) {
						mNoOverageDialog.setRefreshMoney(String.valueOf(mOverage));
						mNoOverageDialog.show();
						return;
					}
					 if(hasCoin<0)
					 {
						 showToast("小巴币不足");
						 return ;
					 }
					 
					 if(mCouponList.size()<0){
						 showToast("小巴券不足");
						 return ;
					 }
							AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, BookCoachReponse.class, new MySubResponseHandler<BookCoachReponse>() {
								@Override
								public void onStart() {
									mLoadingDialog.show();
								}
								
								@Override
								public RequestParams setParams(RequestParams requestParams) {
									requestParams.add("action", "BookCoach");
									requestParams.add("coachid", mCoachId);
									requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
									requestParams.add("date", new Gson().toJson(mRequestData));
									requestParams.add("version",((GuangdaApplication) mBaseApplication).getVersion());
									String ss = new Gson().toJson(mRequestData).toString();
									return requestParams;
								}
								@Override
								public void onFinish() {
									mLoadingDialog.dismiss();
								}
								@Override
								public void onSuccess(int statusCode, Header[] headers, BookCoachReponse baseReponse) {
									float restMoney = canUseCoinsNum - mHasUseResetMoney;
									GuangdaApplication.mUserInfo.setMoney(String.valueOf(restMoney));
									new ReserveSuccessDialog(mBaseFragmentActivity, baseReponse.getSuccessorderid()).show();
								}

								@Override
								public void onNotSuccess(Context context, int statusCode, Header[] headers, BookCoachReponse baseReponse) {
									if (baseReponse.getCode()==-1)
									{
										ReserveNotSuccessDialog notsuccess = new ReserveNotSuccessDialog(mBaseFragmentActivity);
										notsuccess.setMessage("版本太老，请退出应用后再次登录，应用将自动更新","确定");
										notsuccess.show();
									}else if (baseReponse.getCode() == 10){
										ReserveNotSuccessDialog notsuccess = new ReserveNotSuccessDialog(mBaseFragmentActivity);
										notsuccess.setMessage("您预约的时间已被其他的学员抢走了","重新选择时间");
										notsuccess.show();
									}else if(baseReponse.getCode()==4){
										showToast("版本太旧！请更新版本！");
									}else{
										if (baseReponse.getMessage()!=null)
										{
											showToast(baseReponse.getMessage());
										}
									}
									//new ReserveNotSuccessDialog(mBaseFragmentActivity).show();
								}
							});
				}
			}
			}
		});
		
		chosePayWay.tvSure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chosePayWay.dismiss();
			}
		});
		
		chosePayWay.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				switch (chosePayWay.Type) {
				case 1:  //余额
					hasChosedOrderPrice.get(chosedIndex).setType(1);
					if (hasChosedOrderPrice.get(chosedIndex).getCoupon() != null)
					{
						hasChosedOrderPrice.get(chosedIndex).setCoupon(null);
					}
					chosePayWay.dismiss();
					initData();
					break;
				case 2:  //学时券
					hasChosedOrderPrice.get(chosedIndex).setType(2);
					chosePayWay.dismiss();			
					if (hasChosedOrderPrice.get(chosedIndex).getCoupon() != null)
					{
						hasChosedOrderPrice.get(chosedIndex).setCoupon(null);
					}
					initData();
					break;
				case 3:  //小巴币
					hasChosedOrderPrice.get(chosedIndex).setType(3);
					if (hasChosedOrderPrice.get(chosedIndex).getCoupon() != null)
					{
						hasChosedOrderPrice.get(chosedIndex).setCoupon(null);
					}
					chosePayWay.dismiss();
					initData();
					break;
				case 4://混合支付
					hasChosedOrderPrice.get(chosedIndex).setType(4);
					if(hasCoin>0){
						hasChosedOrderPrice.get(chosedIndex).setDemoney((int)hasCoin);
					}
					if (hasChosedOrderPrice.get(chosedIndex).getCoupon() != null)
					{
						hasChosedOrderPrice.get(chosedIndex).setCoupon(null);
					}
					chosePayWay.dismiss();
					initData();
					break;
				default:
					break;
				}
			}
		});
		
		chosePayWay.rlXueshiquan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chosePayWay.imgChosedXueShiQuan.setImageResource(R.drawable.coupon_select);
				if (chosePayWay.Type==3)
				{
					chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_normal);
				}else if (chosePayWay.Type == 1)
				{
					chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_normal);
				}
				else if(chosePayWay.Type==4){
					chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_normal);
					chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_normal);
					
				}
				chosePayWay.Type = 2;

				
			}
		});
		
		chosePayWay.rlXiaobabi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(chosePayWay.Type==4){
				
						chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_normal);
						chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_select);
						chosePayWay.Type=1;

				}else if(hasCoin>0&&hasCoin<hasChosedOrderPrice.get(chosedIndex).getPrice()){
					if(IspayQuan){
						chosePayWay.imgChosedXueShiQuan.setImageResource(R.drawable.coupon_normal);
					}
					chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_select);
					chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_select);
					chosePayWay.Type=4;
				}else{
					chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_select);
					if (chosePayWay.Type == 2)
					{
						chosePayWay.imgChosedXueShiQuan.setImageResource(R.drawable.coupon_normal);
					}else if (chosePayWay.Type == 1)
					{
						chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_normal);
					}
					chosePayWay.Type = 3;
					
				}
				

			}
		});
		
		chosePayWay.rlYue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(chosePayWay.Type==4){
					if(hasCoin>=hasChosedOrderPrice.get(chosedIndex).getPrice()){
						chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_select);
						chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_normal);
						chosePayWay.Type=3;
					}else{
						chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_select);
						chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_select);
						showToast("小巴币不足");
						chosePayWay.Type=4;
					}
				}else{
					if (chosePayWay.Type == 3)
					{
						chosePayWay.imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_normal);
					}else if (chosePayWay.Type == 2)
					{
						chosePayWay.imgChosedXueShiQuan.setImageResource(R.drawable.coupon_normal);
					}
					chosePayWay.imgChosedYuE.setImageResource(R.drawable.coupon_select);
					chosePayWay.Type = 1;
				}


			}
		});
	}

	@Override
	public void initViews() {
		mCommonTitlebar.getCenterTextView().setText("确认订单");

		mNoOverageDialog = new NoOverageDialog(mBaseFragmentActivity);

		EventBus.getDefault().registerSticky(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	// 上一个界面传入的数据
	public void onEventMainThread(LongSparseArray<List<Data>> remain) {
		mRemain = sortSparseArray(remain);
	}

	/**
	 * 排序日期,按升序排序，每天的小时也要进行升序排序
	 * @param datas
	 * @return
	 */
	private LongSparseArray<List<Data>> sortSparseArray(LongSparseArray<List<Data>> datas) {
		List<Long> keys = new ArrayList<Long>();
		for (int i = 0; i < datas.size(); i++) {
			keys.add(datas.keyAt(i));
		}
		Collections.sort(keys);
		LongSparseArray<List<Data>> datas2 = new LongSparseArray<List<Data>>();
		for (int i = 0; i < keys.size(); i++) {
			Collections.sort(datas.get(keys.get(i)));
			datas2.put(keys.get(i), datas.get(keys.get(i)));
		}
		return divideData(datas2);
	}

	/**
	 * 分割成各种订单(日期不同进行分割，小时没有连在一起的也要进行分割)
	 * 
	 * @param datas
	 * @return 分割好后的订单
	 */
	private LongSparseArray<List<Data>> divideData(LongSparseArray<List<Data>> datas) {
		LongSparseArray<List<Data>> datas2 = new LongSparseArray<List<Data>>();
		if (datas != null) {
			for (int i = 0; i < datas.size(); i++) {
				final Long key = datas.keyAt(i);
				List<Data> data = datas.get(key);
				for (int j = 0; j < data.size(); j++) {
					if (j == 0) {
						keyIndex++;
						List<Data> datasub = new ArrayList<Data>();
						datasub.add(data.get(j));
						datas2.put(keyIndex, datasub);
					} else {
//						if (data.get(j).getHour() == datas2.get(keyIndex).get(datas2.get(keyIndex).size() - 1).getHour() + 1) {
//							datas2.get(keyIndex).add(data.get(j));
//						} else {
							keyIndex++;
							List<Data> datasub = new ArrayList<Data>();
							datasub.add(data.get(j));
							datas2.put(keyIndex, datasub);
						//}
					}
				}
			}
		}
		return datas2;
	}

	// 下一个界面选中返回的数据
//	public void onEventMainThread(ComfirmOrderActivityEvent response) {
//		mGetCanUseCouponResponse = response.getmGetCanUseCouponResponse();
//		if (mGetCanUseCouponResponse.getCouponlist() != null) {
//			mCouponList.clear();
//			mCouponList.addAll(mGetCanUseCouponResponse.getCouponlist());
//		}
//		initData();
//	}
	

	private void initData() {
		IspayQuan=false;//是否是小巴券支付
		mHasUseCoinsNum = 0;
		mHasUseCouponMoney = 0;
		mHasUseResetMoney = 0;
		hasCoin = canUseCoinsNum;
		hasMoney = canUseResetMoney;
		mCouponList.clear();
		mCouponList.addAll(mUseCouponList);
		if (mRemain != null && mContentLi != null) {
			mContentLi.removeAllViews();
			mAllMoney = 0f;
			mHasUseCouponMoney = 0f;
			mRequestData.clear();
			// remain.k
			for (int i = 0; i < mRemain.size(); i++) {
				final Long date = mRemain.keyAt(i);
				if (mRemain.get(date) != null && mRemain.get(date).size() != 0) {
					Request request = new Request();
					request.setDelmoney(0);
					View header = getLayoutInflater().inflate(R.layout.comfirm_order_activity_header, null);
					LinearLayout addLi = (LinearLayout) header.findViewById(R.id.li_add);
					TextView headerTv = (TextView) header.findViewById(R.id.tv_order_date);
/*					TextView tv_oupon = (TextView) header.findViewById(R.id.tv_coupon);*/
					final TextView tv_select_oupon = (TextView) header.findViewById(R.id.tv_select_oupon);
//					LinearLayout mCouponLi = (LinearLayout) header.findViewById(R.id.li_coupon);
					tv_select_oupon.setTag(i);
					
					// 点开选择方式
					tv_select_oupon.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							chosedIndex = Integer.parseInt(v.getTag().toString());
							int tickets = mCouponList.size();
//							float hasCoin = canUseCoinsNum - mHasUseCoinsNum;
//							float hasRest = canUseResetMoney - mHasUseResetMoney;
							float price = hasChosedOrderPrice.get(chosedIndex).getPrice();
							int type = hasChosedOrderPrice.get(chosedIndex).getType();
							chosePayWay.Type = type;
							chosePayWay.setChosedItem(type);
							if(type==2){
								IspayQuan=true;
							}
							if(type==4){
								chosePayWay.setItemVisible(tickets,hasCoin,hasMoney,price,type,hasChosedOrderPrice.get(chosedIndex).getDemoney());
							}else if(hasCoin>0){
								chosePayWay.setItemVisible(tickets,hasCoin,hasMoney,price,type,0);
							}else{
								chosePayWay.setItemVisible(tickets,hasCoin,hasMoney,price,type,0);
							}
							
//							String chosedWay = tv_select_oupon.getText().toString();
//							if (chosedWay.equals("学时券"))
//							{
//								chosePayWay.setChosedItem(0);
//							}else if (chosedWay.equals("小巴币")){
//								chosePayWay.setChosedItem(1);
//							}else if (chosedWay.equals("余额"))
//							{
//								chosePayWay.setChosedItem(2);
//							}
							chosePayWay.show();
//							Long index = Long.valueOf(tv_select_oupon.getTag().toString());
//							int size = mRemain.get(index+1).size();
//							if (mGetCanUseCouponResponse != null)
//								EventBus.getDefault().postSticky(mGetCanUseCouponResponse);
//							Intent intent = new Intent(mBaseFragmentActivity, SelectCouponActivity.class);
//							intent.putExtra("size",size);
//							if (mRemain.get(date).get(0) != null)
//								intent.putExtra("mKeyLong", date);
//							startActivity(intent);
						}
					});
					List<Float> price = new ArrayList<Float>();
					
					for (Data data : mRemain.get(date)) {
						if (data != null)
							price.add(data.getPrice());
					}
					float hasUseCouponMoney = 0;
//					if (mCouponList.size() != 0) {
////						mCouponLi.setVisibility(View.VISIBLE);
//						// 获得排序的所有价格的集合
//						List<Float> price = new ArrayList<Float>();
//						for (Data data : mRemain.get(date)) {
//							if (data != null)
//								price.add(data.getPrice());
//						}
//						Collections.sort(price);
//						Collections.reverse(price);
//						float hasUseCouponMoney = 0;
//						long remainCouponCount = 0;
//						// 遍历小巴券列表，计算可以使用小巴券大的金额
//						int hourCoupon = 0;
//						for (Coupon coupon : mCouponList) {
//							// 记录没有使用的现金券
//							if (!coupon.isSelect()) {
//								remainCouponCount++;
//							}
//							if (date == coupon.getKeyLong()) {
//								if (coupon.isSelect()) {
//									request.getRecordid().append(coupon.getRecordid() + ",");
//									if (coupon.getCoupontype() == 1) {
//										// 时间券
//										hourCoupon++;
//									} else {
//										// 抵价券,计算出累计的抵价券
//										mHasUseCouponMoney = mHasUseCouponMoney + coupon.getValue();
//										hasUseCouponMoney = hasUseCouponMoney + coupon.getValue();
//									}
//								}
//							}
//						}
//						if (request.getRecordid().toString().contains(",")) {
//							request.setRecordid(request.getRecordid().deleteCharAt(request.getRecordid().length() - 1));
//						}
//						// 计算出累计的时间券
//						if (hourCoupon > price.size()) {
//							hourCoupon = price.size();
//						}
//						for (int a = 0; a < hourCoupon; a++) {
//							hasUseCouponMoney = hasUseCouponMoney + price.get(a);
//							mHasUseCouponMoney = mHasUseCouponMoney + price.get(a);
//						}
//
////						if (hasUseCouponMoney == 0) {
////							// 没有使用现金券的现实
////							if (remainCouponCount != 0) {
////								SpannableString spannableString = new SpannableString("当前有可用的优惠券");
////								spannableString.setSpan(new AbsoluteSizeSpan(24), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////								spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#737780")), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////								tv_oupon.setText(spannableString);
////							} else if (remainCouponCount == 0) {
////								SpannableString spannableString = new SpannableString("当前无可用的优惠券");
////								spannableString.setSpan(new AbsoluteSizeSpan(24), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////								spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#737780")), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////								tv_oupon.setText(spannableString);
////							}
////						} else {
////							// 选择使用现金券的现实
////							request.setDelmoney((int) hasUseCouponMoney);
////							tv_oupon.setText("");
////							tv_oupon.append("已使用");
////							SpannableString spannableString = new SpannableString(hasUseCouponMoney + "元");
////							spannableString.setSpan(new AbsoluteSizeSpan(24), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////							spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#f7645c")), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////							tv_oupon.append(spannableString);
////							tv_oupon.append("优惠券");
////						}
//						
//						
//						
//
////					} else {
////						mCouponLi.setVisibility(View.GONE);
////					}
//					}
					
					 
					if (isFirstLoad == 0)
					{
						OrderList orderPrice = new OrderList();
					if (mCouponList.size()>0)  //有学时券
					{
						tv_select_oupon.setText("学时券");
						//showToast(price.get(0)+""); //每个订单的价格 
						mHasUseCouponMoney = mHasUseCouponMoney+price.get(0);
//						for (int j = 0;i<mCouponList.size();j++)
//						{
//							if (mCouponList.get(j).isSelect() == false)
//							{
//								mCouponList.get(j).setSelect(true);
//								break;
//							}
//						}
						request.setDelmoney(price.get(0).intValue());
						request.setRecordid(mCouponList.get(0).getRecordid());
						request.setPaytype(2);
						mCouponList.get(0).setSelect(true);
						orderPrice.setCoupon(mCouponList.get(0));
						orderPrice.setType(2);   //type2是小巴券
						orderPrice.setPrice(price.get(0));
						mCouponList.remove(0);
					}else if (hasCoin>=price.get(0)){
						tv_select_oupon.setText("小巴币");
						orderPrice.setType(3);  //tyep=3 小巴币
						orderPrice.setPrice(price.get(0));
						hasCoin = hasCoin - price.get(0);
						mHasUseCoinsNum = mHasUseCoinsNum+price.get(0);
						request.setDelmoney(price.get(0).intValue());
						request.setPaytype(3);
					}
					//混合支付
					else if(hasCoin>0&&hasCoin<price.get(0)){
						tv_select_oupon.setText("小巴币和余额混合支付");
					    orderPrice.setPrice(price.get(0));
					    orderPrice.setType(4); //type=4 混合支付
					    orderPrice.setDemoney((int)hasCoin);
					    orderPrice.setPrice(price.get(0).intValue());
					    mixCoins=hasCoin;
					    request.setDelmoney((int)hasCoin);
					    request.setPaytype(4);
					    request.setTotal(price.get(0).intValue());
					    mHasUseCoinsNum=mHasUseCoinsNum+hasCoin;
					    mHasUseResetMoney=mHasUseResetMoney+price.get(0)-hasCoin;
					    hasMoney=hasMoney-price.get(0)+hasCoin;
					    hasCoin=0;

					}else{
						tv_select_oupon.setText("余额支付");
						orderPrice.setType(1); //type=1 余额
						orderPrice.setPrice(price.get(0)); 
						mHasUseResetMoney = mHasUseResetMoney+price.get(0);
						request.setPaytype(1);
						hasMoney = hasMoney - price.get(0);
					}
					hasChosedOrderPrice.add(orderPrice);   //本地记录订单
					}else{
						switch (hasChosedOrderPrice.get(i).getType()) {
						case 1:
							tv_select_oupon.setText("余额支付");
							request.setPaytype(1);
							hasMoney = hasMoney - price.get(0);
							mHasUseResetMoney = mHasUseResetMoney + price.get(0);
							break;
						case 2:
							tv_select_oupon.setText("学时券");
							mHasUseCouponMoney = mHasUseCouponMoney + price.get(0);
							Coupon coupon = mCouponList.get(0);
							hasChosedOrderPrice.get(chosedIndex).setCoupon(coupon);
							mCouponList.get(0).setSelect(true);
							request.setDelmoney(price.get(0).intValue());
							request.setRecordid(mCouponList.get(0).getRecordid());
							request.setPaytype(2);
							mCouponList.remove(0);

							break;
						case 3:
							tv_select_oupon.setText("小巴币");
							hasCoin = hasCoin - price.get(0);
							mHasUseCoinsNum = mHasUseCoinsNum+price.get(0);
							request.setDelmoney(price.get(0).intValue());
							request.setPaytype(3);
							break;
						case 4:
							tv_select_oupon.setText("小巴币和余额混合支付");
							hasCoin=hasCoin-hasChosedOrderPrice.get(i).getDemoney();
							mHasUseCoinsNum=mHasUseCoinsNum+hasChosedOrderPrice.get(i).getDemoney();
							hasMoney=hasMoney-price.get(0)+hasChosedOrderPrice.get(i).getDemoney();
							mHasUseResetMoney = mHasUseResetMoney + price.get(0)-hasChosedOrderPrice.get(i).getDemoney();
							request.setDelmoney(hasChosedOrderPrice.get(i).getDemoney());
							request.setTotal(price.get(0).intValue());
							request.setPaytype(4);
						default:
							break;
						}
					}
					


					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
					lp.topMargin = 20;
					mContentLi.addView(header, lp);
					int count = mRemain.get(date).size();
					for (int j = 0; j < count; j++) {
						Data data = mRemain.get(date).get(j);
						if (data != null) {
							View item = getLayoutInflater().inflate(R.layout.comfirm_order_activity_item, null);
							TextView time = (TextView) item.findViewById(R.id.tv_time);
							TextView timeSub = (TextView) item.findViewById(R.id.tv_time_sub);
							TextView money = (TextView) item.findViewById(R.id.tv_money);
							TextView sub = (TextView) item.findViewById(R.id.tv_sub);
							TextView address = (TextView) item.findViewById(R.id.tv_address);
							request.getTime().add(data.getHour() + "");
							request.setDate(TimeUitlLj.millisecondsToString(9, data.getDateLong()));
							if (j == 0 && count == 1) {
								headerTv.setText(TimeUitlLj.millisecondsToString(9, data.getDateLong()) + " " + data.getHour() + ":00-" + (data.getHour() + 1) + ":00");
							} else if (j == 0) {
								headerTv.append(TimeUitlLj.millisecondsToString(9, data.getDateLong()) + " " + data.getHour() + ":00-");
							} else if (j == count - 1) {
								headerTv.append((data.getHour() + 1) + ":00");
							}
							time.setText(data.getHour() + ":00");
							timeSub.setText("上午的" + data.getHour() + ":00-" + (data.getHour() + 1) + ":00");
							mAllMoney = mAllMoney + data.getPrice();
							money.setText(data.getPrice() + "元");
							sub.setText(data.getSubject());
							address.setText(data.getAddressdetail());
							addLi.addView(item);
							if (count != 1 && (j != count - 1)) {
								View line = new View(this);
								line.setBackgroundColor(Color.parseColor("#dbdcdf"));
								LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(-1, -2);
								lp2.height = 1;
								lp2.leftMargin = 50;
								lp2.rightMargin = 50;
								addLi.addView(line, lp2);
							}
						}
					}
					mRequestData.add(request);
				}
			}
		}
		//mAllMoneyTv.setText("合计" + (mAllMoney - mHasUseCouponMoney) + "元");
		mAllMoneyTv.setText("合计" + (mAllMoney) + "元");
		if (mHasUseCouponMoney == 0) {
			mHasUseTv.setVisibility(View.GONE);
		} else {
			mHasUseTv.setVisibility(View.VISIBLE);
			mHasUseTv.setText("使用优惠券：抵" + mHasUseCouponMoney + "元");
		}
		
		if (mHasUseCoinsNum == 0)
		{
			tvHasCoin.setVisibility(View.GONE);
		}else{
			tvHasCoin.setVisibility(View.VISIBLE);
			tvHasCoin.setText("使用小巴币："+mHasUseCoinsNum+"个");
		}
		
		if (mHasUseResetMoney == 0)
		{
			tvHasRest.setVisibility(View.GONE);
		}else{
			tvHasRest.setVisibility(View.VISIBLE);
			tvHasRest.setText("使用余额："+mHasUseResetMoney+"元");
		}
		/*
		 * if (mAllMoney != 0f) { mSureOrderTv.setSelected(true); } else { mSureOrderTv.setSelected(false); }
		 */
		if (mRemain != null) {
			mSureOrderTv.setSelected(true);
		} else {
			mSureOrderTv.setSelected(false);
		}
		
		isFirstLoad = 1;
		
		if (hasMoney<0)
		{
			mSureOrderTv.setBackgroundResource(R.drawable.selector_green);
			mSureOrderTv.setText("去充值");
			hasmoneyflag = false;
		}else{
			mSureOrderTv.setBackgroundResource(R.drawable.selector_subject_sure_reserve);
			mSureOrderTv.setText("确认付款");
			hasmoneyflag = true;
		}
	}

	@Override
	public void requestOnCreate() {
		// 请求小巴券的集合
		AsyncHttpClientUtil.get().post(this, Setting.SBOOK_URL, GetCanUseCouponResponse.class, new MySubResponseHandler<GetCanUseCouponResponse>() {
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "getCanUseCouponList");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("coachid", mCoachId);
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetCanUseCouponResponse baseReponse) {
				mGetCanUseCouponResponse = baseReponse;
				if (baseReponse.getCouponlist() != null) {
					mCouponList.clear();
					mCouponList.addAll(baseReponse.getCouponlist());
					canUseCoinsNum = baseReponse.getCoinnum();
					canUseResetMoney = baseReponse.getMoney();
					mUseCouponList.clear();
					mUseCouponList.addAll(baseReponse.getCouponlist());
					initData();
				}
			}
		});
	}
	
//	private void getCoinNum()
//	{
//		AsyncHttpClientUtil.get().post(this, Setting.SBOOK_URL, GetCoinnumResponse.class, new MySubResponseHandler<GetCoinnumResponse>() {
//			@Override
//			public void onStart() {
//				mLoadingDialog.show();
//			}
//
//			@Override
//			public RequestParams setParams(RequestParams requestParams) {
//				requestParams.add("action", "GETCANUSECOINSUM");
//				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
//				requestParams.add("coachid", mCoachId);
//				showToast("inputParamsGet");
//				return requestParams;
//			}
//
//			@Override
//			public void onFinish() {
//				mLoadingDialog.dismiss();
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, GetCoinnumResponse baseReponse) {
//				showToast("getCoinSuccess");
//				if (baseReponse.getCode() == 1)
//				{
//					canUseCoinsNum = baseReponse.getCoinnum();
//					initData();
//				}else{
//					if (TextUtils.isEmpty(baseReponse.getMessage()))
//					{
//						showToast(baseReponse.getMessage());
//					}
//				}
//			}
//		});
//	}
	
}
