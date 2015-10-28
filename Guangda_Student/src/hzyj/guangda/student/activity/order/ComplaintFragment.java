package hzyj.guangda.student.activity.order;

import org.apache.http.Header;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.base.BaseFragment;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetUnCompleteOrderResponse;
import hzyj.guangda.student.response.GetUnCompleteOrderResponse.Order;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.CancelComplaint;
import hzyj.guangda.student.view.CancleOrderDialog;
import hzyj.guangda.student.view.CoachSrueDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ComplaintFragment extends BaseFragment{

	private PtrClassicFrameLayout mPtrClassicFrameLayout;
	private ListView mListView;
	private int mPage;
	private OrderListAdapter mOrderListAdapter;
	private RelativeLayout mNoDataRl;
	private CancelComplaint cancelDialog;
	private MyOrderListActivity mActivity;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.wait_comment_fragment, container, false);

		mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame);
		mPtrClassicFrameLayout.setDurationToCloseHeader(800);
		mListView = (ListView) view.findViewById(R.id.lv_order);

		mNoDataRl = (RelativeLayout) view.findViewById(R.id.rl_no_date);
		
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (MyOrderListActivity) activity;
	}

	@Override
	protected void addListeners(View view, Bundle savedInstanceState) {
		mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				mPage = 0;
				doLoadMoreData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
	}

	public void doLoadMoreData() {
		if (isVisible())
			AsyncHttpClientUtil.get().post(getActivity(), Setting.SORDER_URL, GetUnCompleteOrderResponse.class, new MySubResponseHandler<GetUnCompleteOrderResponse>() {

				@Override
				public RequestParams setParams(RequestParams requestParams) {
					requestParams.add("action", "GETCOMPLAINTORDER");
					requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
					requestParams.add("pagenum", mPage + "");
					return requestParams;
				}

				@Override
				public void onFinish() {
					mPtrClassicFrameLayout.refreshComplete();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, GetUnCompleteOrderResponse baseReponse) {
					initAllData(baseReponse);
				}
			});
	}

	@Override
	public void onStop() {
		super.onStop();
		mListView.smoothScrollToPosition(0);
	}

	private void initAllData(GetUnCompleteOrderResponse baseReponse) {
		if (baseReponse.getOrderlist() != null && baseReponse.getOrderlist().size() != 0) {
			mNoDataRl.setVisibility(View.INVISIBLE);
			mListView.setVisibility(View.VISIBLE);
			if (mPage == 0) {
				mOrderListAdapter.clear();
			}
			if (baseReponse.getHasmore() == 0) {
				mOrderListAdapter.showIndeterminateProgress(false);
			} else if (baseReponse.getHasmore() == 1 && baseReponse.getOrderlist() != null) {
				mOrderListAdapter.showIndeterminateProgress(true);
				mPage++;
			}
			mOrderListAdapter.addAll(baseReponse.getOrderlist());
		} else {
			mNoDataRl.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	protected void initViews(View view, Bundle savedInstanceState) {

		mOrderListAdapter = new OrderListAdapter(getActivity(), R.layout.order_list_item);
		onLoadMoreData(mListView, mOrderListAdapter);
		mListView.setAdapter(mOrderListAdapter);
	}

	@Override
	public void onLasyLoad() {
		super.onLasyLoad();
		mPtrClassicFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrClassicFrameLayout.autoRefresh(true);
			}
		}, 150);
	}

	@Override
	protected void requestOnCreate() {

	}

	private class OrderListAdapter extends QuickAdapter<Order> {

		public OrderListAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final Order item, int position) {
			if (item != null) {
				helper.setText(R.id.tv_address, item.getDetail());
				final TextView name = helper.getView(R.id.tv_coach_name);
				if (item.getCuserinfo() != null) {
					name.setText(item.getCuserinfo().getRealname());
				}
				TextView status = helper.getView(R.id.tv_status);
				final TextView date = helper.getView(R.id.tv_Y_M_R);
				final TextView time = helper.getView(R.id.tv_course_time);
				final TextView all_money = helper.getView(R.id.tv_all_money);
				final TextView carlicense=helper.getView(R.id.tv_carlicense);
				TextView tv_complaint = helper.getView(R.id.tv_complaint);
//				TextView tv_complaint_more = helper.getView(R.id.tv_complaint_more);
				 TextView tv_cancel_complaint = helper.getView(R.id.tv_cancle_complaint);
//				TextView tv_get_on = helper.getView(R.id.tv_get_on);
//				TextView tv_get_off = helper.getView(R.id.tv_get_off);
				TextView tv_cancel_order = helper.getView(R.id.tv_cancel_order);
				TextView tv_comment = helper.getView(R.id.tv_comment);
				//TextView tv_course=helper.getView(R.id.tv_course);
				//TextView tv_continue = helper.getView(R.id.tv_continue);
				//
//				tv_continue.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mBaseFragmentActivity, R.anim.bottom_to_center, R.anim.no_fade);
//						Intent intent = new Intent(mBaseFragmentActivity, SubjectReserveActivity.class);
//						intent.putExtra("mCoachId", item.getCoachid());
//						intent.putExtra("mAddress", item.getDetail());
//						if (item.getCuserinfo() != null) {
//							intent.putExtra("mScore", item.getCuserinfo().getScore());
//							intent.putExtra("mName", item.getCuserinfo().getRealname());
//						}
//						ActivityCompat.startActivity((Activity) mBaseFragmentActivity, intent, options.toBundle());
//					}
//				});
				// 状态
				switch (item.getHours()) {
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
					status.setText("离学车还有" + TimeUitlLj.awayFromFuture(item.getHours() * 60 * 1000));
					status.setTextColor(Color.parseColor("#b8b8b8"));
					break;
				}
				
				if(item.getCarlicense()!=null){
					carlicense.setText("("+item.getCarlicense()+")");
				}else{
					carlicense.setText("");
				}
				//tv_course.setText(item.getSubjectname());
				// date
				long dateStartLong = TimeUitlLj.stringToMilliseconds(2, item.getStart_time());

				date.setText(TimeUitlLj.millisecondsToString(9, dateStartLong));
				// time
				long dateEndLong = TimeUitlLj.stringToMilliseconds(2, item.getEnd_time());
				time.setText(TimeUitlLj.millisecondsToString(10, dateStartLong) + "-" + TimeUitlLj.millisecondsToString(10, dateEndLong));
				// 合计
				all_money.setText(item.getTotal() + "元");
				// 是否可以投诉
				if (item.getCan_complaint() == 0) {
					tv_complaint.setVisibility(View.GONE);
				} else if (item.getCan_complaint() == 1) {
					tv_complaint.setVisibility(View.VISIBLE);
					tv_complaint.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mBaseFragmentActivity, ComplaintActivity.class);
							intent.putExtra("mOrderid", item.getOrderid());
							intent.putExtra("mCreatTime", item.getCreat_time());
							intent.putExtra("mOrderCoach", name.getText().toString().trim());
							intent.putExtra("mOrderTime", date.getText().toString().trim() + " " + time.getText().toString().trim());
							intent.putExtra("mOrderAddress", item.getDetail());
							intent.putExtra("mAllMoney", all_money.getText().toString().trim());
							startActivity(intent);
						}
					});
				}
				// 是否需要取消投诉
				if (item.getNeed_uncomplaint() == 0) {
					//tv_complaint_more.setVisibility(View.GONE);
					tv_cancel_complaint.setVisibility(View.GONE);
				} else if (item.getNeed_uncomplaint() == 1) {
					//tv_complaint_more.setVisibility(View.VISIBLE);
					tv_cancel_complaint.setVisibility(View.VISIBLE);
					tv_complaint.setVisibility(View.GONE);
					//tv_complaint_more.setOnClickListener(new OnClickListener() {
//
//						@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(mBaseFragmentActivity, ComplaintActivity.class);
//						intent.putExtra("mOrderid", item.getOrderid());
//						intent.putExtra("mCreatTime", item.getCreat_time());
//						intent.putExtra("mOrderCoach", name.getText().toString().trim());
//							intent.putExtra("mOrderTime", date.getText().toString().trim() + " " + time.getText().toString().trim());
//							intent.putExtra("mOrderAddress", item.getDetail());
//							intent.putExtra("mAllMoney", all_money.getText().toString().trim());
//							startActivity(intent);
//						}
//					});
					tv_cancel_complaint.setOnClickListener(new OnClickListener() {
						
					@Override
					public void onClick(View v) {
						
						cancelDialog=new CancelComplaint(mActivity,item.getOrderid(),GuangdaApplication.mUserInfo.getStudentid(),mPtrClassicFrameLayout);
						cancelDialog.show();
						
//						AsyncHttpClientUtil.get().post(getActivity(), Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
//							@Override
//							public void onStart() {
//								super.onStart();
//							}
//
//							@Override
//							public RequestParams setParams(RequestParams requestParams) {
//								requestParams.add("action", "CancelComplaint");
//								requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
//								requestParams.add("orderid", item.getOrderid());
//								return requestParams;
//							}
//
//							@Override
//							public void onFinish() {
//								if (mBaseFragmentActivity.mLoadingDialog != null) {
//									mBaseFragmentActivity.mLoadingDialog.dismiss();
//								}
//							}
//
//							@Override
//							public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
//								mPtrClassicFrameLayout.autoRefresh(true);
//							}
//						});
					}
				});
				}
				// 订单是否可以取消
				if (item.getCan_cancel() == 0) {
					tv_cancel_order.setVisibility(View.GONE);
				} else if (item.getCan_cancel() == 1) {
					tv_cancel_order.setVisibility(View.VISIBLE);

				}
				// 订单是否可以确认上车
//				if (item.getCan_up() == 0) {
//					tv_get_on.setVisibility(View.GONE);
//				} else if (item.getCan_up() == 1) {
//					tv_get_on.setVisibility(View.VISIBLE);
//					tv_get_on.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							initLocationClient(item, "ConfirmOn");
//						}
//					});
//				}
				// 订单是否可以确认下车
//				if (item.getCan_down() == 0) {
//					tv_get_off.setVisibility(View.GONE);
//				} else if (item.getCan_down() == 1) {
//					tv_get_off.setVisibility(View.VISIBLE);
//					tv_get_off.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							initLocationClient(item, "ConfirmDown");
//						}
//					});
//				}
				// 订单是否可以评论
				if (item.getCan_comment() == 0) {
					tv_comment.setVisibility(View.GONE);
				} else if (item.getCan_comment() == 1) {
					tv_comment.setVisibility(View.VISIBLE);
					tv_comment.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(mBaseFragmentActivity, CommentActivity.class);
							intent.putExtra("mOrderid", item.getOrderid());
							intent.putExtra("mCreatTime", item.getCreat_time());
							intent.putExtra("mOrderCoach", name.getText().toString().trim());
							intent.putExtra("mOrderTime", date.getText().toString().trim() + " " + time.getText().toString().trim());
							intent.putExtra("mOrderAddress", item.getDetail());
							intent.putExtra("mAllMoney", all_money.getText().toString().trim());
							startActivity(intent);
						}
					});
				}
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mBaseFragmentActivity, OrderDetailActivity.class);
						intent.putExtra("mOrderid", item.getOrderid());
						intent.putExtra("mOrderTime", date.getText().toString().trim() + " " + time.getText().toString().trim());
						intent.putExtra("mAllMoney", all_money.getText().toString().trim());
						intent.putExtra("flag","ComplaintFragment");
						startActivity(intent);
					}
				});
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
				AsyncHttpClientUtil.get().post(getActivity(), Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
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
					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
						mPtrClassicFrameLayout.autoRefresh();
					}
				});
			}
		});
		mLocClient.start();
	}


}
