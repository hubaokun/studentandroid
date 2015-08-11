package hzyj.guangda.student.activity.order;

import hzyj.guangda.student.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.library.llj.base.BaseFragmentActivity;

/**
 * 
 * @author liulj
 * 
 */
public class MyOrderListActivity extends BaseFragmentActivity {
	private TextView mTitleLeftTv, mTitleCenterTv, mTitleRightTv;
	private ImageView mBackIv;
	private ViewPager mViewPager;
	private TabAdapter mTabAdapter;

	@Override
	public int getLayoutId() {
		return R.layout.my_orderlist_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mBackIv = (ImageView) findViewById(R.id.iv_back);

		mTitleLeftTv = (TextView) findViewById(R.id.tv_title_left);
		mTitleCenterTv = (TextView) findViewById(R.id.tv_title_center);
		mTitleRightTv = (TextView) findViewById(R.id.tv_title_right);
		mViewPager = (ViewPager) findViewById(R.id.viewPager_tab);
	}

	@Override
	public void addListeners() {
		mBackIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		mTitleLeftTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTitleLeftTv.setSelected(true);
				mTitleCenterTv.setSelected(false);
				mTitleRightTv.setSelected(false);
				mViewPager.setCurrentItem(0);

			}
		});
		mTitleCenterTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTitleLeftTv.setSelected(false);
				mTitleCenterTv.setSelected(true);
				mTitleRightTv.setSelected(false);
				mViewPager.setCurrentItem(1);

			}
		});
		mTitleRightTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTitleLeftTv.setSelected(false);
				mTitleCenterTv.setSelected(false);
				mTitleRightTv.setSelected(true);
				mViewPager.setCurrentItem(2);
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					mTitleLeftTv.performClick();
					break;
				case 1:
					mTitleCenterTv.performClick();
					break;
				case 2:
					mTitleRightTv.performClick();
					break;
				}
			}

		});
	}

	@Override
	public void initViews() {
		mTitleLeftTv.setSelected(true);

		mTabAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mTabAdapter);
	}

	@Override
	public void requestOnCreate() {

	}

	private class TabAdapter extends FragmentPagerAdapter {

		public TabAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				return new NotFinishedFragment();
			case 1:
				return new WaitCommentFragment();
			case 2:
				return new FinishedFragment();

			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}

	}
}
