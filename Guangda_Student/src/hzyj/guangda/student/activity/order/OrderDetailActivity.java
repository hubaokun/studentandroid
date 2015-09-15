package hzyj.guangda.student.activity.order;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.SubjectReserveActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.CoachListEvent;
import hzyj.guangda.student.event.OrderDetailEvent;
import hzyj.guangda.student.response.OrderDetailResponse;
import hzyj.guangda.student.response.OrderDetailResponse.Order;
import hzyj.guangda.student.response.OrderDetailResponse.Order.Evaluation;
import hzyj.guangda.student.response.OrderDetailResponse.Order.Hour;
import hzyj.guangda.student.response.OrderDetailResponse.Order.MyEvaluation;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.CancelComplaint;
import hzyj.guangda.student.view.CancleOrderDialog;
import hzyj.guangda.student.view.CoachSrueDialog;
import hzyj.guangda.student.view.CoachSrueDialog.onButtonClickListener;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.DecimalUtil;
import com.common.library.llj.utils.ParseUtilLj;
import com.common.library.llj.utils.TextUtilLj;
import com.common.library.llj.utils.TimeUitlLj;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author liulj
 * 
 */
public class OrderDetailActivity extends BaseFragmentActivity implements onButtonClickListener{
	private static final int GONE = 0;
	private TextView mNameTv, mDateTv, mTimeTv, mPhoneTv, mMobileTv, mAddressTv, mAllMoneyTv,mOrderId,mcourse;
	private TextView tv_complaint/*, tv_complaint_more*/, tv_cancel_complaint, tv_get_on, tv_get_off, tv_continue,tv_cancel_order, tv_comment;

	private RatingBar mStarRb;
	private LinearLayout mPerPriceLi;
	private TextView mPerPriceTagTv, mPriceTagTv, mStanderTv;

	private TextView mCoachScoreTv, mCoachCommentTv;
	private RatingBar mCoachRb;
	private TextView mMyScoreTv, mMyCommentTv;
	private RatingBar mMyRb;

	private String mOrderid, mOrderTime, mAllMoney,FragmentName="",studentid,studentstate="",coachstate="";
	private TextView mContinueTv,iv_cancle;
	private OrderDetailResponse mOrderDetailResponse;
	
	private ImageView iv_back,iv_explant,iv_cancle_explant;
	private CancleOrderDialog cancleorderdialog;
	private LinearLayout ll_coach_sure,ll_complaintcontent,ll_tiao;
	private CoachSrueDialog coachsure;
	private TextView status,tv_complaintcontent;
	private String complaintcontent;
	private RelativeLayout rl_explant,rl_cancle_explant;
	private CancelComplaint cancelDialog;
	
	@Override
	public void getIntentData() {
		super.getIntentData();
		mOrderid = getIntent().getStringExtra("mOrderid");
		mOrderTime = getIntent().getStringExtra("mOrderTime");
		mAllMoney = getIntent().getStringExtra("mAllMoney");
		FragmentName=getIntent().getStringExtra("flag");
		studentid=getIntent().getStringExtra("studentid");
		studentstate=getIntent().getStringExtra("studentstate");
		coachstate=getIntent().getStringExtra("coachstate");
	}

	@Override
	public int getLayoutId() {
		return R.layout.order_detail_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		//mContinueTv = (TextView) findViewById(R.id.tv_continue);

		mNameTv = (TextView) findViewById(R.id.tv_name);
		mOrderId=(TextView)findViewById(R.id.tv_orderid);
		mDateTv = (TextView) findViewById(R.id.tv_date);
		mTimeTv = (TextView) findViewById(R.id.tv_time);
		//mPhoneTv = (TextView) findViewById(R.id.tv_phone);
		mMobileTv = (TextView) findViewById(R.id.tv_mobile);
		mAddressTv = (TextView) findViewById(R.id.tv_address);
		//mPerPriceLi = (LinearLayout) findViewById(R.id.li_per_price);
		mAllMoneyTv = (TextView) findViewById(R.id.tv_all_price);
		mStarRb = (RatingBar) findViewById(R.id.rb_star);
		tv_complaint = (TextView) findViewById(R.id.tv_complaint);
//		tv_complaint_more = (TextView) findViewById(R.id.tv_complaint_more);
		tv_cancel_complaint = (TextView) findViewById(R.id.tv_cancel_complaint);
		//tv_get_on = (TextView) findViewById(R.id.tv_get_on);
		tv_get_off = (TextView) findViewById(R.id.tv_get_off);
		//tv_cancel_order = (TextView) findViewById(R.id.tv_cancel_order);
		tv_comment = (TextView) findViewById(R.id.tv_order_comment);

		mStanderTv = (TextView) findViewById(R.id.tv_stander);
		mcourse=(TextView)findViewById(R.id.tv_course);
		//mPerPriceTagTv = (TextView) findViewById(R.id.tv_price_tag);
		//mPriceTagTv = (TextView) findViewById(R.id.tv_all_price_tag);
		//mPerPriceTagTv.getLayoutParams().width = (int) TextUtilLj.getTextViewLength(mStanderTv, "下单时间：");
		//mPriceTagTv.getLayoutParams().width = (int) TextUtilLj.getTextViewLength(mStanderTv, "下单时间：");

		mCoachScoreTv = (TextView) findViewById(R.id.tv_score);
		mCoachCommentTv = (TextView) findViewById(R.id.tv_comment);
		mCoachRb = (RatingBar) findViewById(R.id.rb_score);

		mMyScoreTv = (TextView) findViewById(R.id.tv_my_score);
		mMyCommentTv = (TextView) findViewById(R.id.tv_my_comment);
		mMyRb = (RatingBar) findViewById(R.id.rb_my_score);
		tv_continue=(TextView)findViewById(R.id.tv_continue);
		
		
		ll_coach_sure=(LinearLayout)findViewById(R.id.ll_coach_sure);
		status=(TextView)findViewById(R.id.tv_state);
		ll_complaintcontent=(LinearLayout)findViewById(R.id.ll_complaintcontent);
		tv_complaintcontent=(TextView)findViewById(R.id.tv_complaintcontent);
		rl_explant=(RelativeLayout)findViewById(R.id.rl_explant);
		rl_cancle_explant=(RelativeLayout)findViewById(R.id.rl_cancle_explant);
		ll_tiao=(LinearLayout)findViewById(R.id.ll_tiao);
		//导航栏
		iv_back=(ImageView)findViewById(R.id.iv_back);
		
		iv_cancle=(TextView)findViewById(R.id.tv_cancle_order);
		
		if(FragmentName.equals("FinishedFragment")){
			tv_continue.setVisibility(View.VISIBLE);
		}
		if (coachstate!=null&&studentstate!=null)
		{
		if(!coachstate.equals("4")&&studentstate.equals("4")){
			ll_coach_sure.setVisibility(View.VISIBLE);
		}
		}
	}

	@Override
	public void addListeners() {
		
		iv_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
			
		});
		
		rl_explant.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//tv_complaintcontent.setText("#"+mOrderDetailResponse.getOrderinfo().getReason()+"#"+complaintcontent);
				tv_complaintcontent.setText(Html.fromHtml("<font color=#50cb8 size=30dp>#</font>"+"<font color=#50cb8>"+mOrderDetailResponse.getOrderinfo().getReason()+"</font>"+"<font color=#50cb8>#</font>"+complaintcontent));
				rl_explant.setVisibility(View.GONE);
				ll_tiao.setVisibility(View.VISIBLE);
				rl_cancle_explant.setVisibility(View.VISIBLE);
				
			}
		});
		rl_cancle_explant.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tv_complaintcontent.setText("您的投诉小巴正在加急处理,请耐性等待!");
				rl_explant.setVisibility(View.VISIBLE);
				ll_tiao.setVisibility(View.GONE);
				rl_cancle_explant.setVisibility(View.GONE);
				
			}
		});
		
		coachsure=new CoachSrueDialog(this,mOrderid,studentid);
		cancelDialog=new CancelComplaint(this,mOrderid,GuangdaApplication.mUserInfo.getStudentid());
		
//		iv_cancle.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				coachsure.show();
//			}
//			
//		});
		
		//继续预约操作
		tv_continue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mBaseFragmentActivity, R.anim.bottom_to_center, R.anim.no_fade);
				Intent intent = new Intent(mBaseFragmentActivity, SubjectReserveActivity.class);
				if (mOrderDetailResponse != null && mOrderDetailResponse.getOrderinfo() != null) {
					intent.putExtra("mCoachId", mOrderDetailResponse.getOrderinfo().getCoachid());
					intent.putExtra("mAddress", mOrderDetailResponse.getOrderinfo().getDetail());
					if (mOrderDetailResponse.getOrderinfo().getCuserinfo() != null)
						intent.putExtra("mName", mOrderDetailResponse.getOrderinfo().getCuserinfo().getRealname());
				}
				intent.putExtra("mScore", "0");
				ActivityCompat.startActivity((Activity) mBaseFragmentActivity, intent, options.toBundle());
			}
		});
	}
	
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
	}

	@Override
	public void initViews() {
		//mCommonTitlebar.getCenterTextView().setText("车单详细");
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, OrderDetailResponse.class, new MySubResponseHandler<OrderDetailResponse>() {

			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETORDERDETAIL");
				requestParams.add("orderid", mOrderid);
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, OrderDetailResponse orderDetailResponse) {
				initData(orderDetailResponse);
			}
		});
	}

	@Override
	public void requestOnCreate() {
		
	}

	private void initData(final OrderDetailResponse orderDetailResponse) {
		mOrderDetailResponse = orderDetailResponse;
		if (orderDetailResponse.getOrderinfo() != null) {
			
			switch (mOrderDetailResponse.getOrderinfo().getHours()) {
			case 0:
				status.setText("此车单即将开始");
				status.setTextColor(Color.parseColor("#f7645c"));
				break;
			case -1:
				status.setText("正在学车");
				status.setTextColor(Color.parseColor("#50cb8c"));
				break;
			case -2:
				status.setText("学车完成");
				status.setTextColor(Color.parseColor("#f7645c"));
				break;
			case -3:
				status.setText("待确认上车");
				status.setTextColor(Color.parseColor("#f7645c"));
				break;
			case -4:
				status.setText("待确认下车");
				status.setTextColor(Color.parseColor("#f7645c"));
				break;
			case -5:
				status.setText("投诉处理中");
				status.setTextColor(Color.parseColor("#FF4500"));
				break;
			case -6:
				status.setText("客服协调中");
				status.setTextColor(Color.parseColor("#FF4500"));
				break;
			default:
				status.setText("离学车还有" + TimeUitlLj.awayFromFuture(mOrderDetailResponse.getOrderinfo().getHours() * 60 * 1000));
				status.setTextColor(Color.parseColor("#b8b8b8"));
				break;
			}
			
			if (orderDetailResponse.getOrderinfo().getCuserinfo() != null) {
				// 姓名
				setText(mNameTv, orderDetailResponse.getOrderinfo().getCuserinfo().getRealname() + " " + "教练");
				// 星级
				//mStarRb.setRating(orderDetailResponse.getOrderinfo().getCuserinfo().getScore());
				// 教练电话
				//setText(mPhoneTv, orderDetailResponse.getOrderinfo().getCuserinfo().getTelphone());
				// 教练手机
				setText(mMobileTv, orderDetailResponse.getOrderinfo().getCuserinfo().getPhone());
				
				//订单编号
				setText(mOrderId, orderDetailResponse.getOrderinfo().getOrderid());
			}
			
			if(mOrderDetailResponse.getOrderinfo().getHours()==-5){
				ll_complaintcontent.setVisibility(View.VISIBLE);
				rl_explant.setVisibility(View.VISIBLE);
			}else{
				ll_complaintcontent.setVisibility(View.GONE);
				rl_explant.setVisibility(View.GONE);
			}
			complaintcontent=orderDetailResponse.getOrderinfo().getComplaintcontent();
			// 下单时间
			if (orderDetailResponse.getOrderinfo().getCreat_time() != null) {
				// long mill = TimeUitlLj.stringToMilliseconds(2, orderDetailResponse.getOrderinfo().getCreat_time());
				// String dateStr = TimeUitlLj.millisecondsToString(9, mill);
				// mDateTv.setText(dateStr);
				mDateTv.setText(orderDetailResponse.getOrderinfo().getCreat_time()+" ");
			}
			// 预约时间
			// date
			long dateStartLong = TimeUitlLj.stringToMilliseconds(2, orderDetailResponse.getOrderinfo().getStart_time());
			//mTimeTv.setText(TimeUitlLj.millisecondsToString(9, dateStartLong));
			// time
			long dateEndLong = TimeUitlLj.stringToMilliseconds(2, orderDetailResponse.getOrderinfo().getEnd_time());
			mTimeTv.setText(TimeUitlLj.millisecondsToString(9, dateStartLong)+" "+TimeUitlLj.millisecondsToString(10, dateStartLong) + "-" + TimeUitlLj.millisecondsToString(10, dateEndLong));
			
			//mTimeTv.append(" " + TimeUitlLj.millisecondsToString(10, dateStartLong) + "-" + TimeUitlLj.millisecondsToString(10, dateEndLong));
           
			// 预约地址
			setText(mAddressTv, orderDetailResponse.getOrderinfo().getDetail());
			//科目
			setText(mcourse, orderDetailResponse.getOrderinfo().getOrderprice().get(0).getSubject());
			// 单价
//			mPerPriceLi.removeAllViews();
//			for (Hour hour : orderDetailResponse.getOrderinfo().getOrderprice()) {
//				if (hour != null) {
//					TextView textView = new TextView(this);
//					String time = hour.getHour() + ":00-" + (hour.getHour() + 1) + ":00";
//					//String money = hour.getPrice() + "元";
//					textView.setText(time);
//					textView.setTextColor(getResources().getColor(R.color.text));
//					textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
//					mPerPriceLi.addView(textView);
//				}
//			}
			// 合计
			mAllMoneyTv.setText(orderDetailResponse.getOrderinfo().getTotal() + "元");
			

				if (orderDetailResponse.getOrderinfo().getCan_complaint() == 0) {
					tv_complaint.setVisibility(View.GONE);
				} else if (orderDetailResponse.getOrderinfo().getCan_complaint() == 1) {
					tv_complaint.setVisibility(View.VISIBLE);
					tv_complaint.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mBaseFragmentActivity, ComplaintActivity.class);
							Order item = orderDetailResponse.getOrderinfo();
							intent.putExtra("mOrderid", item.getOrderid());
							intent.putExtra("mCreatTime", item.getCreat_time());
							intent.putExtra("mOrderCoach",item.getCuserinfo().getRealname());
							intent.putExtra("mOrderTime", mOrderTime);
							intent.putExtra("mOrderAddress", item.getDetail());
							intent.putExtra("mAllMoney", mAllMoney);
							startActivity(intent);
						}
					});
				}
				
				if (orderDetailResponse.getOrderinfo().getNeed_uncomplaint() == 0) {
					tv_cancel_complaint.setVisibility(View.GONE);
					//tv_complaint.setVisibility(View.GONE);
				} else if (orderDetailResponse.getOrderinfo().getNeed_uncomplaint() == 1) {
					tv_cancel_complaint.setVisibility(View.VISIBLE);
					//tv_complaint.setVisibility(View.GONE);
					tv_cancel_complaint.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Order item = orderDetailResponse.getOrderinfo();
							//cancelDialog=new CancelComplaint(this,item.getOrderid(),GuangdaApplication.mUserInfo.getStudentid());
							cancelDialog.show();
//							AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
//								@Override
//								public void onStart() {
//									super.onStart();
//									mBaseFragmentActivity.mLoadingDialog.show();
//								}
//
//								@Override
//								public RequestParams setParams(RequestParams requestParams) {
//									Order item = orderDetailResponse.getOrderinfo();
//									requestParams.add("action", "CancelComplaint");
//									requestParams.add("orderid", item.getOrderid());
//									requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
//									return requestParams;
//								}
//
//								@Override
//								public void onFinish() {
//									mBaseFragmentActivity.mLoadingDialog.dismiss();
//								}
//
//								@Override
//								public void onSuccess(int statusCode, Header[] headers, BaseReponse reponse) {
//									EventBus.getDefault().post(new OrderDetailEvent(orderDetailResponse.getOrderinfo().getOrderid()));
//									finish();
//								}
//							});
						}
					});
				}
			
			// 是否可以投诉
			if (orderDetailResponse.getOrderinfo().getCan_complaint() == 0) {
				tv_complaint.setVisibility(View.GONE);
			} else if (orderDetailResponse.getOrderinfo().getCan_complaint() == 1) {
				tv_complaint.setVisibility(View.VISIBLE);
				tv_complaint.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mBaseFragmentActivity, ComplaintActivity.class);
						Order item = orderDetailResponse.getOrderinfo();
						intent.putExtra("mOrderid", item.getOrderid());
						intent.putExtra("mCreatTime", item.getCreat_time());
						intent.putExtra("mOrderCoach",item.getCuserinfo().getRealname());
						intent.putExtra("mOrderTime", mOrderTime);
						intent.putExtra("mOrderAddress", item.getDetail());
						intent.putExtra("mAllMoney", mAllMoney);
						startActivity(intent);
					}
				});
			}
			// 是否需要追加投诉
//			if (orderDetailResponse.getOrderinfo().getNeed_uncomplaint() == 0) {
//				//tv_complaint_more.setVisibility(View.GONE);
//			} else if (orderDetailResponse.getOrderinfo().getNeed_uncomplaint() == 1) {
//				//tv_complaint_more.setVisibility(View.VISIBLE);
//				tv_complaint.setVisibility(View.GONE);
//				tv_complaint_more.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
//							@Override
//							public void onStart() {
//								super.onStart();
//								mBaseFragmentActivity.mLoadingDialog.show();
//							}
//
//							@Override
//							public RequestParams setParams(RequestParams requestParams) {
//								requestParams.add("action", "CancelComplaint");
//								requestParams.add("orderid", orderDetailResponse.getOrderinfo().getOrderid());
//								return requestParams;
//							}
//
//							@Override
//							public void onFinish() {
//								mBaseFragmentActivity.mLoadingDialog.dismiss();
//							}
//
//							@Override
//							public void onSuccess(int statusCode, Header[] headers, BaseReponse reponse) {
//								showToast("取消投诉成功");
//							}
//						});
//					}
//				});
//			}
			// 是否要取消投诉
//			if (orderDetailResponse.getOrderinfo().getNeed_uncomplaint() == 0) {
//				tv_cancel_complaint.setVisibility(View.GONE);
//				tv_complaint.setVisibility(View.VISIBLE);
//			} else if (orderDetailResponse.getOrderinfo().getNeed_uncomplaint() == 1) {
//				tv_cancel_complaint.setVisibility(View.VISIBLE);
//				tv_complaint.setVisibility(View.GONE);
//				tv_cancel_complaint.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
//							@Override
//							public void onStart() {
//								super.onStart();
//								mBaseFragmentActivity.mLoadingDialog.show();
//							}
//
//							@Override
//							public RequestParams setParams(RequestParams requestParams) {
//								Order item = orderDetailResponse.getOrderinfo();
//								requestParams.add("action", "CancelComplaint");
//								requestParams.add("orderid", item.getOrderid());
//								requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
//								return requestParams;
//							}
//
//							@Override
//							public void onFinish() {
//								mBaseFragmentActivity.mLoadingDialog.dismiss();
//							}
//
//							@Override
//							public void onSuccess(int statusCode, Header[] headers, BaseReponse reponse) {
//								EventBus.getDefault().post(new OrderDetailEvent(orderDetailResponse.getOrderinfo().getOrderid()));
//								finish();
//							}
//						});
//					}
//				});
//			}
			// 订单是否可以确认上车
//			if (orderDetailResponse.getOrderinfo().getCan_up() == 0) {
//				tv_get_on.setVisibility(View.GONE);
//			} else if (orderDetailResponse.getOrderinfo().getCan_up() == 1) {
//				tv_get_on.setVisibility(View.VISIBLE);
//				tv_get_on.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						initLocationClient(orderDetailResponse.getOrderinfo(), "ConfirmOn");
//					}
//				});
//			}
			// 订单是否可以确认下车
			if (orderDetailResponse.getOrderinfo().getCan_down() == 0) {
				tv_get_off.setVisibility(View.GONE);
			} else if (orderDetailResponse.getOrderinfo().getCan_down() == 1) {
				tv_get_off.setVisibility(View.VISIBLE);
				tv_complaint.setVisibility(View.VISIBLE);
				tv_get_off.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						initLocationClient(orderDetailResponse.getOrderinfo(), "ConfirmDown");
						Intent intent = new Intent(mBaseFragmentActivity, CommentActivity.class);
						intent.putExtra("mOrderid", orderDetailResponse.getOrderinfo().getOrderid());
						intent.putExtra("mCreatTime",orderDetailResponse.getOrderinfo().getCreat_time());
						intent.putExtra("mOrderCoach",orderDetailResponse.getOrderinfo().getCoachname());
						intent.putExtra("mOrderTime", orderDetailResponse.getOrderinfo().getStart_time());
						intent.putExtra("mOrderAddress",orderDetailResponse.getOrderinfo().getDetail());
						intent.putExtra("mAllMoney",orderDetailResponse.getOrderinfo().getTotal() + "元");
						startActivity(intent);	
					}
				});
			}
			// 订单是否可以评论
			if (orderDetailResponse.getOrderinfo().getCan_comment() == 0) {
				tv_comment.setVisibility(View.GONE);
			} else if (orderDetailResponse.getOrderinfo().getCan_comment() == 1) {
				tv_comment.setVisibility(View.VISIBLE);
				tv_comment.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Order item = orderDetailResponse.getOrderinfo();
						Intent intent = new Intent(mBaseFragmentActivity, CommentActivity.class);
						intent.putExtra("mOrderid", item.getOrderid());
						intent.putExtra("mCreatTime", item.getCreat_time());
						if (item.getCuserinfo() != null)
						intent.putExtra("mOrderCoach", item.getCuserinfo().getRealname());
						intent.putExtra("mOrderTime", mOrderTime);
						intent.putExtra("mOrderAddress", item.getDetail());
						intent.putExtra("mAllMoney", mAllMoney);
						startActivity(intent);
					}
				});
			}
			// 订单是否可以取消
			if (orderDetailResponse.getOrderinfo().getCan_cancel() == 0) {
				int a=orderDetailResponse.getOrderinfo().getCan_cancel();
				iv_cancle.setVisibility(View.GONE);
			} else if (orderDetailResponse.getOrderinfo().getCan_cancel() == 1) {
				iv_cancle.setVisibility(View.VISIBLE);
				iv_cancle.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						coachsure.show();
					}
				});
			
			}
			// 我给教练的评价
			if (orderDetailResponse.getOrderinfo().getEvaluation() != null) {
				Evaluation evaluation = orderDetailResponse.getOrderinfo().getEvaluation();
				String score = DecimalUtil.formatHalfUp(evaluation.getScore(), 1);
				mCoachScoreTv.setText(score + "分");
				mCoachRb.setRating(ParseUtilLj.parseFloat(score, 5));
				setText(mCoachCommentTv, evaluation.getContent());
			}
			// 教练给我的评价
			if (orderDetailResponse.getOrderinfo().getMyevaluation() != null) {
				MyEvaluation myevaluation = orderDetailResponse.getOrderinfo().getMyevaluation();
				String score = DecimalUtil.formatHalfUp(myevaluation.getScore(), 1);
				mMyScoreTv.setText(score + "分");
				mMyRb.setRating(ParseUtilLj.parseFloat(score, 5));
				setText(mMyCommentTv, myevaluation.getContent());

			}
		}
	}

	/**
	 * 开启定位获取经纬度，并发起请求
	 * 
	 * @param item
	 *            实体对象
	 * @param action
	 */
	private void initLocationClient(final Order item, final String action) {
		LocationClient mLocClient = new LocationClient(mBaseFragmentActivity);
		LocationClientOption option = new LocationClientOption();
		// option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		mLocClient.setLocOption(option);
		mLocClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(final BDLocation arg0) {
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
					@Override
					public void onStart() {
						super.onStart();
						mBaseFragmentActivity.mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", action);
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						requestParams.add("orderid", item.getOrderid());
						requestParams.add("lat", arg0.getLatitude() + "");
						requestParams.add("lon", arg0.getLongitude() + "");
						requestParams.add("detail", arg0.getAddrStr());
						return requestParams;
					}

					@Override
					public void onFinish() {
						mBaseFragmentActivity.mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, BaseReponse reponse) {
						requestOnCreate();
						
					}
				});
			}
		});
		mLocClient.start();
	}

	@Override
	public void onregion(String message) {
		// TODO Auto-generated method stub
		if(message.equals("正在确认")){
			ll_coach_sure.setVisibility(View.VISIBLE);
		}
		
		
	}
}
