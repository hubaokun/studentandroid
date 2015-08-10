package hzyj.guangda.student.activity;

import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.entity.Coupon;
import hzyj.guangda.student.event.ComfirmOrderActivityEvent;
import hzyj.guangda.student.response.GetCanUseCouponResponse;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.utils.TimeUitlLj;

import de.greenrobot.event.EventBus;

/**
 * 选择优惠券
 * 
 * @author liulj
 * 
 */
public class SelectCouponActivity extends TitlebarActivity {
	private ListView mListView;
	private CradAdapter mCradAdapter;
	private int canUseDiff;// 一个订单是否可以使用不同的订单 0:不可以 1：可以
	private int canUseMaxCount; // 可以使用的最大的小巴券数量，
	private int mSelectCount;// 自己选择的小巴券数量
	private int coupontype;// 小巴券类型1：时间券 2：抵价券
	private GetCanUseCouponResponse mGetCanUseCouponResponse;
	private long mKeyLong;
	private int size;

	@Override
	public void getIntentData() {
		super.getIntentData();
		mKeyLong = getIntent().getLongExtra("mKeyLong", 0);
		size = getIntent().getIntExtra("size", 0);
	}

	@Override
	public int getLayoutId() {
		return R.layout.select_coupon_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mListView = (ListView) findViewById(R.id.lv_card);
	}

	@Override
	public void addListeners() {
		mCommonTitlebar.setLeftTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (Coupon coupon : mCradAdapter.getList()) {
					setResponseSelectOrNot(coupon);
				}
				EventBus.getDefault().unregister(this);
				if (mGetCanUseCouponResponse != null) {
					EventBus.getDefault().post(new ComfirmOrderActivityEvent(mGetCanUseCouponResponse));
				}
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		EventBus.getDefault().unregister(this);
		for (Coupon coupon : mCradAdapter.getList()) {
			reSetResponseSelect(coupon);
		}
		if (mGetCanUseCouponResponse != null) {
			EventBus.getDefault().post(new ComfirmOrderActivityEvent(mGetCanUseCouponResponse));
		}
		super.onBackPressed();
	}

	@Override
	public void initViews() {
		setCenterText("选择优惠券");
		setRightText("选好了", 10, R.color.text_light_green);
		mCradAdapter = new CradAdapter(mBaseFragmentActivity, R.layout.select_coupon_item);
		mListView.setAdapter(mCradAdapter);
		EventBus.getDefault().registerSticky(this);
	}

	public void onEventMainThread(GetCanUseCouponResponse response) {
		mGetCanUseCouponResponse = response;
		canUseDiff = response.getCanUseDiff();
		canUseMaxCount = response.getCanUseMaxCount();
		if (canUseMaxCount>size)
		{
			canUseMaxCount = size;
		}
		List<Coupon> temp = new ArrayList<Coupon>();
		if (response.getCouponlist() != null) {
			mCradAdapter.clear();
			for (Coupon coupon : response.getCouponlist()) {
				// 0代表还没有使用，和mKeyLong相等，说明已经被当前订单使用
				if (coupon.getKeyLong() == 0 || coupon.getKeyLong() == mKeyLong)
					temp.add(coupon);

			}
			mCradAdapter.addAll(temp);
		}
	}

	@Override
	public void requestOnCreate() {

	}

	/**
	 * 
	 * @author liulj
	 * 
	 */
	private class CradAdapter extends QuickAdapter<Coupon> {

		public CradAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final Coupon item, int position) {
			if (item != null) {
				TextView title = helper.getView(R.id.tv_title);
				TextView sub_title = helper.getView(R.id.tv_sub_title);
				TextView from = helper.getView(R.id.tv_from);
				TextView end_time = helper.getView(R.id.tv_limit);
				final ImageView select = helper.getView(R.id.iv_select);
				// 标题
				if (item.getCoupontype() == 1) {
					title.setText("小巴券-学时券");
					sub_title.setText("本券可以抵用1学时学费");
				} else if (item.getCoupontype() == 2) {
					title.setText("小巴券-抵价券");
					sub_title.setText("本券可以抵用学费" + item.getValue() + "元");
				}
				// 来源
				from.setText(item.getTitle());
				// // 来源
				// switch (item.getOwnertype()) {
				// case 0:
				// from.setText("由广大官方发布");
				// break;
				// case 1:
				// from.setText("由驾校发布");
				// break;
				// case 3:
				// from.setText("由教练发布");
				// break;
				// }
				// 期限
				if (!TextUtils.isEmpty(item.getEnd_time())) {
					end_time.setText("有效期：" + TimeUitlLj.millisecondsToString(8, TimeUitlLj.stringToMilliseconds(2, item.getEnd_time())));
				}
				//
				select.setSelected(item.isSelect());
				// 设置选中的数量
				if (item.isSelect()) {
					mSelectCount++;
				}
				coupontype = item.getCoupontype();
				//
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (item.isSelect()) {
							if (mSelectCount == 1) {
								coupontype = 0;
							}
							mSelectCount--;
							select.setSelected(false);
							item.setSelect(false);
							// setResponseSelectOrNot(item);
						} else {
							//
							if (mSelectCount < canUseMaxCount) {
								// 都没有选的时候肯定可以选
								if (mSelectCount == 0) {
									mSelectCount++;
									// 设置小巴券类型
									coupontype = item.getCoupontype();
									select.setSelected(true);
									item.setSelect(true);
									// setResponseSelectOrNot(item);
								} else {
									if (canUseDiff == 0) {
										// 不可以使用不同类型情况
										if (coupontype == item.getCoupontype()) {
											mSelectCount++;
											select.setSelected(true);
											item.setSelect(true);
											// setResponseSelectOrNot(item);
										} else {
											showToast("您不能使用不同类型的小巴券！");
										}
									} else if (canUseDiff == 1) {
										// 可以使用不同类型
										mSelectCount++;
										select.setSelected(true);
										item.setSelect(true);
										// setResponseSelectOrNot(item);
									}
								}

							} else {
								showToast("已经超出了可使用的小巴券的数量！");
							}

						}
					}
				});
			}
		}
	}

	private void reSetResponseSelect(Coupon item) {
		if (mGetCanUseCouponResponse.getCouponlist() != null && item != null)
			for (Coupon coupon : mGetCanUseCouponResponse.getCouponlist()) {
				if (coupon != null && coupon.getRecordid() != null && coupon.getRecordid().equals(item.getRecordid())) {
					coupon.setSelect(false);
					// 如果没有选中则设置已经被当前订单选用
					if (coupon.getKeyLong() == mKeyLong) {
						// 否则设置为0，为没有使用
						coupon.setKeyLong(0);
					}
				}
			}

	}

	/**
	 * 设置传入的数据的选中或者不选中
	 * 
	 * @param item
	 */
	private void setResponseSelectOrNot(Coupon item) {
		if (mGetCanUseCouponResponse.getCouponlist() != null && item != null)
			for (Coupon coupon : mGetCanUseCouponResponse.getCouponlist()) {
				if (coupon != null && coupon.getRecordid() != null && coupon.getRecordid().equals(item.getRecordid()) && item.isSelect()) {
					coupon.setSelect(item.isSelect());
					// 如果没有选中则设置已经被当前订单选用
					if (coupon.getKeyLong() == 0) {
						coupon.setKeyLong(mKeyLong);
					}
				}
			}
	}

}
