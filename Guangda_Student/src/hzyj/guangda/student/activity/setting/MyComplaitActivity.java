package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.order.ComplaintActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.ComplaintResponse;
import hzyj.guangda.student.response.ComplaintResponse.Complaint;
import hzyj.guangda.student.response.ComplaintResponse.Complaint.Content;
import hzyj.guangda.student.util.MySubResponseHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import org.apache.http.Header;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.DensityUtils;
import com.common.library.llj.utils.TimeUitlLj;
import com.common.library.llj.views.LinearListView;
import com.loopj.android.http.RequestParams;

/**
 * 我投诉的
 * 
 * @author liulj
 * 
 */
public class MyComplaitActivity extends TitlebarActivity {
	private PtrClassicFrameLayout mPtrFrameLayout;
	private ListView mListView;
	private int mPage;
	private ComplaintAdapter mComplaintAdapter;

	// private RelativeLayout mNoDataRl;

	@Override
	public int getLayoutId() {
		return R.layout.my_complait_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame);
		mPtrFrameLayout.setDurationToCloseHeader(800);
		mListView = (ListView) findViewById(R.id.lv_complaint);

		// mNoDataRl = (RelativeLayout) findViewById(R.id.rl_no_date);
	}

	@Override
	public void addListeners() {
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
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

	@Override
	public void initViews() {
		setCenterText("我的投诉");

		mComplaintAdapter = new ComplaintAdapter(this, R.layout.my_complaint_activity_item);
		onLoadMoreData(mListView, mComplaintAdapter);
		mListView.setAdapter(mComplaintAdapter);

	}

	@Override
	public void requestOnCreate() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh(true);
			}
		}, 150);
	}

	public void doLoadMoreData() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, ComplaintResponse.class, new MySubResponseHandler<ComplaintResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetMyComplaint");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("pagenum", mPage + "");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mPtrFrameLayout.refreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, ComplaintResponse baseReponse) {
				initAllData(baseReponse);
			}
		});
	}

	private void initAllData(ComplaintResponse baseReponse) {
		if (baseReponse.getComplaintlist() != null && baseReponse.getComplaintlist().size() != 0) {
			// mNoDataRl.setVisibility(View.INVISIBLE);
			mListView.setVisibility(View.VISIBLE);
			if (mPage == 0) {
				mComplaintAdapter.clear();
			}
			if (baseReponse.getHasmore() == 0) {
				mComplaintAdapter.showIndeterminateProgress(false);
			} else if (baseReponse.getHasmore() == 1 && baseReponse.getComplaintlist() != null) {
				mComplaintAdapter.showIndeterminateProgress(true);
				mPage++;
			}
			mComplaintAdapter.addAll(baseReponse.getComplaintlist());
		} else {
			// mNoDataRl.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.INVISIBLE);
		}
	}

	private class ComplaintAdapter extends QuickAdapter<Complaint> {

		public ComplaintAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final Complaint item, int position) {
			if (item != null) {
				boolean isSolve = true;

				ImageView imageView = helper.getView(R.id.iv_image);
				TextView type = helper.getView(R.id.tv_type);
				final TextView time = helper.getView(R.id.tv_time);
				TextView tv_cancel_complaint = helper.getView(R.id.tv_cancel_complaint);
//				TextView tv_complaint_more = helper.getView(R.id.tv_complaint_more);
				TextView tagTv = helper.getView(R.id.tv_tag);
				LinearLayout mWrapLi = helper.getView(R.id.li_wrap);
				LinearListView lv_content = helper.getView(R.id.lv_content);
				lv_content.removeAllViews();
				// 显示名字
				helper.setText(R.id.tv_name, item.getName());
				// 显示头像
				loadHeadImage(item.getCoachavatar(), 74, 74, imageView);
				// 显示内容
				if (item.getContentlist() != null && item.getContentlist().size() != 0) {
					tagTv.setVisibility(View.VISIBLE);
					lv_content.setVisibility(View.VISIBLE);
					for (Content content : item.getContentlist()) {
						if (content.getContent() != null) {
							TextView textView = new TextView(mBaseFragmentActivity);
							LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
							if (content.getState() == 0) {
								// 未解决(绿色)
								if (content.getReason() != null) {
									SpannableString fmoney = new SpannableString("#" + content.getReason().trim() + "#");
									fmoney.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(mBaseFragmentActivity, 15)), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
									fmoney.setSpan(new ForegroundColorSpan(Color.parseColor("#50cb8c")), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
									textView.append(fmoney);
								}
								textView.append(content.getContent());
								isSolve = false;
							} else {
								// 已解决
								if (content.getReason() != null) {
									SpannableString fmoney = new SpannableString("#" + content.getReason().trim() + "#");
									fmoney.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(mBaseFragmentActivity, 15)), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
									fmoney.setSpan(new ForegroundColorSpan(Color.parseColor("#737780")), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
									textView.append(fmoney);
								}
								textView.append(content.getContent().trim());
							}
							lv_content.addView(textView, lp);
						}
					}
				} else {
					tagTv.setVisibility(View.GONE);
					lv_content.setVisibility(View.GONE);
				}
				// 显示状态
				if (isSolve) {
					type.setText("已解决");
					type.setTextColor(Color.parseColor("#50cb8c"));
					mWrapLi.setVisibility(View.GONE);
				} else {
					type.setText("正在处理中");
					type.setTextColor(Color.parseColor("#f7645c"));
					mWrapLi.setVisibility(View.VISIBLE);
					tv_cancel_complaint.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
								@Override
								public void onStart() {
									super.onStart();
									mLoadingDialog.show();
									mLoadingDialog.setOnDismissListener(new OnDismissListener() {

										@Override
										public void onDismiss(DialogInterface dialog) {
										}
									});
								}

								@Override
								public RequestParams setParams(RequestParams requestParams) {
									requestParams.add("action", "CancelComplaint");
									requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
									if (item.getContentlist() != null && item.getContentlist().size() != 0) {
										if (item.getContentlist().get(0) != null)
											requestParams.add("orderid", item.getContentlist().get(0).getOrder_id());
									}
									return requestParams;
								}

								@Override
								public void onFinish() {
									mLoadingDialog.dismiss();
								}

								@Override
								public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
									mPtrFrameLayout.autoRefresh();
								}
							});
						}
					});
//					tv_complaint_more.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							Intent intent = new Intent(mBaseFragmentActivity, ComplaintActivity.class);
//							if (item.getContentlist() != null && item.getContentlist().size() != 0) {
//								if (item.getContentlist().get(0) != null)
//									intent.putExtra("mOrderid", item.getContentlist().get(0).getOrder_id());
//								intent.putExtra("mCreatTime", item.getContentlist().get(0).getAddtime());
//							}
//							intent.putExtra("mOrderCoach", item.getName());
//							intent.putExtra("mOrderTime", time.getText().toString().trim());
//							intent.putExtra("mOrderAddress", item.getDetail());
//							intent.putExtra("mAllMoney", item.getTotal());
//							startActivity(intent);
//						}
//					});

				}
				// 任务时间
				long dateStartLong = TimeUitlLj.stringToMilliseconds(2, item.getStarttime());
				String dateStartStr1 = TimeUitlLj.millisecondsToString(9, dateStartLong);
				String dateStartStr = TimeUitlLj.millisecondsToString(10, dateStartLong);
				long dateEndLong = TimeUitlLj.stringToMilliseconds(2, item.getEndtime());
				String dateEndStr = TimeUitlLj.millisecondsToString(10, dateEndLong);
				if (dateStartStr1 != null && dateStartStr != null && dateEndStr != null) {
					time.setVisibility(View.VISIBLE);
					time.setText("");
					SpannableString fmoney = new SpannableString("任务时间");
					fmoney.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(mBaseFragmentActivity, 12)), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					fmoney.setSpan(new ForegroundColorSpan(Color.parseColor("#5b5a5a")), 0, fmoney.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					time.append(fmoney + " ");
					time.append(dateStartStr1 + " " + dateStartStr + "-" + dateEndStr);
				} else {
					time.setVisibility(View.INVISIBLE);
				}

			}
		}
	}
}
