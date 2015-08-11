package com.common.library.llj.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.common.library.llj.utils.LogUtilLj;

/**
 * menu类
 * 
 * @author liulj
 * 
 */
public abstract class BaseMenuActivity extends BaseFragmentActivity {
	private FragmentManager mFragmentManager;
	public int mHideItem;
	public int mShowItem;

	@Override
	public abstract int getLayoutId();

	@Override
	public void findViews(Bundle savedInstanceState) {
		mFragmentManager = getSupportFragmentManager();
		// 从savedInstanceState获取到保存的mCurrentItem
		if (savedInstanceState != null) {
			mHideItem = savedInstanceState.getInt("mHideItem", 1);
			mShowItem = savedInstanceState.getInt("mShowItem", 1);
		}
	};

	public abstract void findViewsWrap(Bundle savedInstanceState);

	@Override
	public abstract void addListeners();

	@Override
	public abstract void initViews();

	@Override
	public abstract void requestOnCreate();

	/**
	 * 有可能被意外销毁之前调用，主动销毁不调用 1.按home回到主页
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mHideItem", mHideItem);
		outState.putInt("mShowItem", mShowItem);

		LogUtilLj.LLJi("onSaveInstanceState");
	}

	/**
	 * 隐藏当前显示的fragment,显示将要显示的fragment
	 * 
	 * @param curItem
	 *            当前选择项1,2,3,4,5
	 */
	public void selectItem(int hideItem, int showItem, boolean isOnCreate) {
		// 获得将要显示页的tag
		String currentTag = "fragment" + hideItem;
		// 隐藏当前的的fragment
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		// 如果被杀后再进来，全部的fragment都会被呈现显示状态，所以都隐藏一边
		if (isOnCreate && mFragmentManager.getFragments() != null) {
			for (Fragment fragment : mFragmentManager.getFragments()) {
				transaction.hide(fragment);
			}
		} else {
			// 正常按钮点击进入
			Fragment lastFragment = mFragmentManager.findFragmentByTag(currentTag);
			if (lastFragment != null) {
				transaction.hide(lastFragment);
			}
		}
		// 获得将要显示页的tag
		String toTag = "fragment" + showItem;
		// find要显示的Fragment
		Fragment currentFragment = mFragmentManager.findFragmentByTag(toTag);
		if (currentFragment != null) {
			// 已经存在则显示
			transaction.attach(currentFragment);
		} else {
			// 不存在则添加新的fragment
			currentFragment = makeFragment(showItem);
			if (currentFragment == null) {
				throw new NullPointerException("Fragment为null");
			}
			if (currentFragment != null) {
				// transaction.add(R.id.fl_contain, currentFragment, toTag);
			}
		}
		// 选择image图片
		setSelectImage(showItem);
		// 保存当前显示fragment的item
		mHideItem = hideItem;
		mShowItem = showItem;
		transaction.commit();
	}

	/**
	 * 
	 * @param showItem
	 * @return
	 */
	public abstract Fragment makeFragment(int showItem);

	/**
	 * 
	 * @param showItem
	 */
	public abstract void setSelectImage(int showItem);
}
