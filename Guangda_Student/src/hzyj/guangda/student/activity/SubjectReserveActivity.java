package hzyj.guangda.student.activity;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.activity.setting.RechargeActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.CoachScheduleResponse;
import hzyj.guangda.student.response.CoachScheduleResponse.Data;
import hzyj.guangda.student.response.UserMoneyResponse;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.NeedRealNameDialog;
import hzyj.guangda.student.view.NoOverageDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.LongSparseArray;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.base.BaseFragment;
import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.common.library.llj.views.PagerSlidingTabStrip;
import com.common.library.llj.views.UnscrollableGridView;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 课程预约界面
 * 
 * @author liulj
 * 
 */
public class SubjectReserveActivity extends BaseFragmentActivity {
	private RatingBar mStarRb;// 星级评分条
	private TextView mNameTv, mAddressTv, mTvGender;// 名字，地址
	private ImageView mBackIv;// 返回按钮
	private PagerSlidingTabStrip mTab;// 头部选择滚动条
	private ViewPager mViewPager;//
	private String mCoachId;// 教练id
	private float mScore;// 星级
	private String mName;// 名字
	private String mAddress;// 地址
	private List<Long> mDates = new ArrayList<Long>();// 年月日的毫秒数
	private List<String> mYears = new ArrayList<String>();// 年
	private List<String> mMonths = new ArrayList<String>();// 月/日
	private TextView mSelectNumTv, mAllMoneyTv, mSureTv/*,mRemainMoneyTv*/;// 已经选择几个小时，合计总数，确定预约，自己的余额
	private int mSelectSub;// 选择的小时数
	private LongSparseArray<List<Data>> mRemain = new LongSparseArray<List<Data>>();
	private float mOverage, mAllMoney;// 用户自己的余额，选中的金额总数
/*	private TextView mReChargeTv;*/
	private String mGender;
	private NeedRealNameDialog realNameDialog;
	private TextView tvPhone;
	private LinearLayout llPhone;
	private String mPhone;
	private List<Data> dataArraylist = new ArrayList<CoachScheduleResponse.Data>();
//	private int SelectCount = 0;
	//private boolean isResume = false;
	@Override
	public void getIntentData() {
		mCoachId = getIntent().getStringExtra("mCoachId");
		mScore = getIntent().getFloatExtra("mScore", 0f);
		mName = getIntent().getStringExtra("mName");
		mAddress = getIntent().getStringExtra("mAddress");
		mGender = getIntent().getStringExtra("mGender");
		mPhone = getIntent().getStringExtra("mPhone");
	}

	@Override
	public int getLayoutId() {
		return R.layout.subject_reserve_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mStarRb = (RatingBar) findViewById(R.id.rb_star);
		mNameTv = (TextView) findViewById(R.id.tv_name);
		mAddressTv = (TextView) findViewById(R.id.tv_address);
		mBackIv = (ImageView) findViewById(R.id.iv_back);
		mTvGender = (TextView) findViewById(R.id.tv_gender);
		mTab = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mTab.setUnderlineHeight(0);
		mTab.setIndicatorHeight(0);
		mTab.setDividerColorResource(android.R.color.transparent);
		mViewPager = (ViewPager) findViewById(R.id.viewPager_tab);
		mSelectNumTv = (TextView) findViewById(R.id.tv_select_num);
		mAllMoneyTv = (TextView) findViewById(R.id.tv_all_money);
		mSureTv = (TextView) findViewById(R.id.tv_sure);
//		mRemainMoneyTv = (TextView) findViewById(R.id.tv_remain_money);
//		mReChargeTv = (TextView) findViewById(R.id.tv_recharge);
		tvPhone = (TextView)findViewById(R.id.tv_phone);
		llPhone = (LinearLayout)findViewById(R.id.ll_phone);
	}

	@Override
	public void addListeners() {
		mBackIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mSureTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSureTv.isSelected()) {
					EventBus.getDefault().postSticky(mRemain);
					Intent intent = new Intent(mBaseFragmentActivity, ComfirmOrderActivity.class);
					intent.putExtra("mCoachId", mCoachId);
					intent.putExtra("mOverage", mOverage);
					startActivity(intent);
				}
			}
		});
//		mReChargeTv.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				startMyActivity(RechargeActivity.class);
//			}
//		});
		
		llPhone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvPhone.getText().toString().trim()));
				startActivity(intent);
			}
		});
		
	}

	@Override
	public void initViews() {
		mStarRb.setRating(mScore);
		mNameTv.setText(mName);
		mAddressTv.setText(mAddress);
		mTvGender.setText(mGender);
		mSelectNumTv.setText("您还未选择任何时间");
		initDates();
		EventBus.getDefault().register(this);
		realNameDialog = new NeedRealNameDialog(mBaseFragmentActivity);
		realNameDialog.setCanceledOnTouchOutside(true); // dialog 点击外部消失
		tvPhone.setText(mPhone);
	}

	public void onEventMainThread(Update update) {
		if (update.getType().equals("SubjectReserveActivity")) {
			mRemain.clear();
			mSelectSub = 0;
			mSelectNumTv.setText("您还未选择任何时间");
			mAllMoney = 0f;
			mAllMoneyTv.setVisibility(View.GONE);
			mSureTv.setSelected(false);
		}
	}

	private void initDates() {
		mDates.clear();
		mYears.clear();
		mMonths.clear();
		String dateStr = TimeUitlLj.dateToString(9, new Date());
		Long dateLong = TimeUitlLj.stringToMilliseconds(9, dateStr);
		// 计算当前时间延后30天内的日期
		for (int i = 0; i < 10; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(dateLong + i * 24 * 3600 * 1000l));
			mYears.add(calendar.get(Calendar.YEAR) + "");
			mMonths.add((calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
			mDates.add(calendar.getTime().getTime());
		}
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mTab.setViewPager(mViewPager);
	}

	@Override
	public void requestOnCreate() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		//isResume = true;
		// 刷新用户的余额
		
		
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SYSTEM_URL, UserMoneyResponse.class, new MySubResponseHandler<UserMoneyResponse>() {

			@Override
			public void onStart() {
				
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "refreshUserMoney");
				requestParams.add("userid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("usertype", "2");
				return requestParams;
			}

			@Override
			public void onFinish() {
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, UserMoneyResponse baseReponse) {
				mOverage = baseReponse.getMoney();
//				mRemainMoneyTv.setText("账户余额：" + baseReponse.getMoney() + "元");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.SubTabProvider {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return mDates.size();
		}

		@Override
		public Fragment getItem(int position) {
			SubFragment fragment = new SubFragment();
			Bundle bundle = new Bundle();
			bundle.putLong("date", mDates.get(position));
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public String getPageSubTitle(int position) {
			return mYears.get(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mMonths.get(position);
		}

		@Override
		public int getPageBottomResId(int position) {
			return R.drawable.subject_indicator_img;
		}
	}

	/**
	 * 
	 * @author liulj
	 * 
	 */
	private class SubFragment extends BaseFragment {
		private Long dateLong;
		private UnscrollableGridView mMorningGv, mAfternoonGv, mNightGv;
		private MorningAdapter mMorningAdapter;
		private AfternoonAdapter mAfternoonAdapter;
		private NightAdapter mNightAdapter;
		private NoOverageDialog mNoOverageDialog;

		@Override
		protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			dateLong = getArguments().getLong("date");
			View view = inflater.inflate(R.layout.sub_fragment, container, false);
			mMorningGv = (UnscrollableGridView) view.findViewById(R.id.gv_morning);
			mAfternoonGv = (UnscrollableGridView) view.findViewById(R.id.gv_afternoon);
			mNightGv = (UnscrollableGridView) view.findViewById(R.id.gv_night);
			return view;
		}

		@Override
		protected void addListeners(View view, Bundle savedInstanceState) {

		}
		@Override
		protected void initViews(View view, Bundle savedInstanceState) {
			mMorningAdapter = new MorningAdapter(mBaseFragmentActivity, R.layout.sub_fragment_item, null);
			mMorningGv.setAdapter(mMorningAdapter);
			mAfternoonAdapter = new AfternoonAdapter(mBaseFragmentActivity, R.layout.sub_fragment_item, null);
			mAfternoonGv.setAdapter(mAfternoonAdapter);
			mNightAdapter = new NightAdapter(mBaseFragmentActivity, R.layout.sub_fragment_item, null);
			mNightGv.setAdapter(mNightAdapter);
			mNoOverageDialog = new NoOverageDialog(mBaseFragmentActivity);
		}

		@Override
		protected void requestOnCreate() {
		}

		@Override
		public void onLasyLoad() {
			super.onLasyLoad();
			doRequest();
		}

		private void doRequest() {
			AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, CoachScheduleResponse.class, new MySubResponseHandler<CoachScheduleResponse>() {
				@Override
				public void onStart() {
					mLoadingDialog.setCanceledOnTouchOutside(false);
					mLoadingDialog.show();
				}

				@Override
				public RequestParams setParams(RequestParams requestParams) {
					requestParams.add("action", "RefreshCoachSchedule");
					requestParams.add("coachid", mCoachId);
					requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
					requestParams.add("date", TimeUitlLj.millisecondsToString(9, dateLong));
					return requestParams;
				}

				@Override
				public void onFinish() {
					mLoadingDialog.dismiss();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, CoachScheduleResponse baseReponse) {
					if (baseReponse.getDatelist() != null && baseReponse.getDatelist().size() == 19) {

						dataArraylist = baseReponse.getDatelist();
						System.out.println(baseReponse);
						mMorningAdapter.replaceAll(baseReponse.getDatelist().subList(0, 7));
						mAfternoonAdapter.replaceAll(baseReponse.getDatelist().subList(7, 14));
						mNightAdapter.replaceAll(baseReponse.getDatelist().subList(14, 19));
//						if (!isResume)
//						{
//							SelectCount = 0;
//						}
//						isResume = false;
//						Toast.makeText(SubjectReserveActivity.this, String.valueOf(SelectCount), 0).show();
					}
				}

			});
		}

		private class MorningAdapter extends QuickAdapter<Data> {

			public MorningAdapter(Context context, int layoutResId, List<Data> data) {
				super(context, layoutResId, data);
			}

			@Override
			protected void convert(BaseAdapterHelper helper, View convertView, final Data item, int position) {
				initListviewData(helper, convertView, item, position, 1);
			}
		}

		private boolean retainInMapAndRemove(Data item) {
			if (mRemain.get(dateLong) != null) {
				Iterator<Data> iter = mRemain.get(dateLong).iterator();
				while (iter.hasNext()) {
					Data data = iter.next();
					if ((data.getHour() == item.getHour())) {
						mRemain.get(dateLong).remove(data);
						return true;
					}
				}
			}
			return false;
		}

		private boolean retainInMapUpdateStatus(Data item) {
			if (mRemain.get(dateLong) != null) {
				Iterator<Data> iter = mRemain.get(dateLong).iterator();
				while (iter.hasNext()) {
					Data data = iter.next();
					if ((data.getHour() == item.getHour())) {
						item.setStatus(2);
						return true;
					}
				}
			}
			return false;
		}

		private class AfternoonAdapter extends QuickAdapter<Data> {

			public AfternoonAdapter(Context context, int layoutResId, List<Data> data) {
				super(context, layoutResId, data);
			}

			@Override
			protected void convert(BaseAdapterHelper helper, View convertView, final Data item, int position) {
				initListviewData(helper, convertView, item, position, 2);
			}
		}

		private class NightAdapter extends QuickAdapter<Data> {

			public NightAdapter(Context context, int layoutResId, List<Data> data) {
				super(context, layoutResId, data);
			}

			@Override
			protected void convert(BaseAdapterHelper helper, View convertView, final Data item, int position) {
				initListviewData(helper, convertView, item, position, 3);
			}
		}

		/**
		 * 
		 * @param helper
		 * @param convertView
		 * @param item
		 * @param position
		 * @param type
		 *            123
		 */
		private void initListviewData(BaseAdapterHelper helper, View convertView, final Data item, final int position, int type) {
			if (item != null) {
				final LinearLayout wrap = helper.getView(R.id.li_wrap);
				final TextView time = helper.getView(R.id.tv_time);
				final TextView sub = helper.getView(R.id.tv_sub);
				final TextView money = helper.getView(R.id.tv_money);
				
				final ImageView ok = helper.getView(R.id.iv_ok);
				switch (type) {
				case 1:
					time.setText((position + 5) + ":00");
					item.setHour(position + 5);
					break;
				case 2:

					time.setText((position + 12) + ":00");
					item.setHour(position + 12);
					break;
				case 3:

					time.setText((position + 19) + ":00");
					item.setHour(position + 19);
					break;
				}
				item.setDateLong(dateLong);
				if(item.getSubject()==null)
				{
					sub.setText("");
				}else{
					sub.setText(item.getSubject().trim());
					if ("科目三".equals(item.getSubject().trim()))
					{
						sub.setTextColor(Color.parseColor("#ff7b11"));
					}else{
						sub.setTextColor(Color.parseColor("#348899"));
					}
				}
				if (item.getIsrest() == 1) {
					retainInMapAndRemove(item);
					// 休息
					time.setSelected(false);
					sub.setVisibility(View.INVISIBLE);
					money.setText("休息");
					money.setTextColor(Color.parseColor("#b3b3b3"));
					item.setStatus(0);
					wrap.setBackgroundResource(R.drawable.shape_sub_fragment_cant_select);
				} else if (item.getIsrest() == 0) {
					if (item.getIsbooked() == 1) {
						retainInMapAndRemove(item);
						time.setSelected(false);
						sub.setVisibility(View.INVISIBLE);
						money.setText("教练已被别人预约");
						money.setTextColor(Color.parseColor("#b3b3b3"));
						item.setStatus(0);
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_cant_select);
					} else if (item.getIsbooked() == 2) {
						retainInMapAndRemove(item);
						time.setSelected(false);
						sub.setVisibility(View.INVISIBLE);
						money.setText("您已预约这个教练");
						money.setTextColor(Color.parseColor("#f7645c"));
						item.setStatus(0);
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_cant_select);
					} else if (item.getIsbooked() == 3) {
						retainInMapAndRemove(item);
						time.setSelected(false);
						sub.setVisibility(View.INVISIBLE);
						money.setText("您已预约其他教练");
						money.setTextColor(Color.parseColor("#b3b3b3"));
						item.setStatus(0);
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_cant_select);
//						SelectCount++;
//						Toast.makeText(SubjectReserveActivity.this, String.valueOf(SelectCount), 0).show();
					} else {
						time.setSelected(true);
						sub.setVisibility(View.VISIBLE);
						//sub.setTextColor(Color.parseColor("#348899"));
						money.setText(item.getPrice() + "元");
						money.setTextColor(Color.parseColor("#348899"));
						item.setStatus(1);
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_normal_select);
					}
					retainInMapUpdateStatus(item);
					if (item.getStatus() == 1) {
						// 正常状态，蓝色
						time.setSelected(true);
						ok.setVisibility(View.INVISIBLE);
						sub.setVisibility(View.VISIBLE);
						//sub.setTextColor(Color.parseColor("#348899"));
						money.setVisibility(View.VISIBLE);
						money.setTextColor(Color.parseColor("#348899"));
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_normal_select);
						// 判断是否过时
						if (item.getPasttime() == 1) {
							time.setSelected(false);
							ok.setVisibility(View.INVISIBLE);
							sub.setVisibility(View.VISIBLE);
							sub.setTextColor(Color.parseColor("#b3b3b3"));
							money.setVisibility(View.VISIBLE);
							money.setTextColor(Color.parseColor("#b3b3b3"));
							item.setStatus(0);
							wrap.setBackgroundResource(R.drawable.shape_sub_fragment_cant_select);
						}
					} else if (item.getStatus() == 2) {
						// 选中状态，绿色
						time.setSelected(true);
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_has_select);
						ok.setVisibility(View.VISIBLE);
						sub.setVisibility(View.INVISIBLE);
						money.setVisibility(View.INVISIBLE);
//						SelectCount++;
//						Toast.makeText(SubjectReserveActivity.this, String.valueOf(SelectCount), 0).show();
					} else {
						time.setSelected(false);
						ok.setVisibility(View.INVISIBLE);
						sub.setVisibility(View.INVISIBLE);
						money.setVisibility(View.VISIBLE);
						money.setTextColor(Color.parseColor("#b3b3b3"));
						if (item.getIsbooked() == 2) {
							money.setTextColor(Color.parseColor("#f7645c"));
						}
						item.setStatus(0);
						wrap.setBackgroundResource(R.drawable.shape_sub_fragment_cant_select);
					}

					wrap.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							System.out.println(mRemain.get(dateLong));
							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
								startMyActivity(LoginActivity.class);
								return;
							}
							if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getRealname()))
							{
								realNameDialog.show();
								return;
							}
							if (item.getStatus() == 1) {
								if (mSelectSub ==6){
									showToast("一次选课不能超过6个课时");
									return;
								}
//								if (SelectCount<4)
//								{
								mAllMoney = mAllMoney + item.getPrice();
								// if (mAllMoney > mOverage) {
								// // 还远金额
								// mAllMoney = mAllMoney - item.getPrice();
								// // 余额不足
								// mNoOverageDialog.show();
								// return;
								// }
								if (mRemain.get(dateLong) == null) {
									List<Data> datas = new ArrayList<Data>();
									datas.add(item);
									mRemain.put(dateLong, datas);
								} else {
									mRemain.get(dateLong).add(item);
								}
								wrap.setBackgroundResource(R.drawable.shape_sub_fragment_has_select);
								time.setSelected(true);
								ok.setVisibility(View.VISIBLE);
								sub.setVisibility(View.INVISIBLE);
								money.setVisibility(View.INVISIBLE);
								item.setStatus(2);
								dataArraylist.get(item.getHour()-5).setStatus(2);
								//showToast(item.getHour()-5+"");
								int lastHour = item.getHour()-1;
								int nextHour = item.getHour()+1;
								for (int i=0;i<dataArraylist.size();i++)
								{
									if (dataArraylist.get(i).getHour()==lastHour||dataArraylist.get(i).getHour()==nextHour)
									{
										if (dataArraylist.get(i).getStatus()==2)
										{
											Toast.makeText(SubjectReserveActivity.this, "连续上课会很累的哦，慎重考虑哦亲", Toast.LENGTH_SHORT).show();
//											return;
										}
									}
								}
								mSelectSub++;
//								SelectCount++;
//								Toast.makeText(SubjectReserveActivity.this, String.valueOf(SelectCount), 0).show();
								if (mSelectSub != 0) {
									mSelectNumTv.setText("已选择" + mSelectSub + "个小时");
									mSureTv.setSelected(true);
								} else {
									mSelectNumTv.setText("您还未选择任何时间");
									mSureTv.setSelected(false);
								}

								if (mAllMoney != 0) {
									mAllMoneyTv.setVisibility(View.VISIBLE);
									mAllMoneyTv.setText("合计" + mAllMoney + "元");
								} else {
									mAllMoneyTv.setVisibility(View.GONE);
								}
//								}else{
//									Toast.makeText(SubjectReserveActivity.this,"一天中不能预约超过4个学时的课程",Toast.LENGTH_SHORT).show();
//								}
							} else if (item.getStatus() == 2) {
								wrap.setBackgroundResource(R.drawable.shape_sub_fragment_normal_select);
								time.setSelected(true);
								ok.setVisibility(View.INVISIBLE);
								sub.setVisibility(View.VISIBLE);
								money.setVisibility(View.VISIBLE);
								item.setStatus(1);
								dataArraylist.get(item.getHour()-5).setStatus(1);
								if (mRemain.get(dateLong) != null) {
									Iterator<Data> iter = mRemain.get(dateLong).iterator();
									while (iter.hasNext()) {
										Data data = iter.next();
										if ((data.getHour() == item.getHour())) {
											mRemain.get(dateLong).remove(data);
											break;
										}
									}
								}
								mSelectSub--;
//								SelectCount--;
//								Toast.makeText(SubjectReserveActivity.this, String.valueOf(SelectCount), 0).show();
								if (mSelectSub != 0) {
									mSelectNumTv.setText("已选择" + mSelectSub + "个小时");
									mSureTv.setSelected(true);
								} else {
									mSelectNumTv.setText("您还未选择任何时间");
									mSureTv.setSelected(false);
								}
								mAllMoney = mAllMoney - item.getPrice();
								if (mAllMoney != 0) {
									mAllMoneyTv.setVisibility(View.VISIBLE);
									mAllMoneyTv.setText("合计" + mAllMoney + "元");
								} else {
									mAllMoneyTv.setVisibility(View.GONE);
								}
							}
						}
					});
				}
			}
		}

	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.no_fade, R.anim.center_to_bottom);
	}
}
