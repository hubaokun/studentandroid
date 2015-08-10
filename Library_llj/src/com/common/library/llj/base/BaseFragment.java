package com.common.library.llj.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.lifecycle.LifecycleDispatcher;
import com.common.library.llj.utils.LogUtilLj;

/**
 * 这里做一些共同的事情，比如check
 * 
 * @author liulj
 * 
 */
public abstract class BaseFragment extends Fragment {
	/**
	 * 保存在onDestroyView中remove的view,这样onCreateView的时候不用重新inflater，复用之前的view 这种情况只在attach和detach下，hide和show不用
	 */
	private View mPreView;
	private boolean mIsInit;
	private boolean mIsVisible;
	protected BaseFragmentActivity mBaseFragmentActivity;
	protected BaseApplication mBaseApplication;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mBaseFragmentActivity = (BaseFragmentActivity) activity;
		mBaseApplication = (BaseApplication) mBaseFragmentActivity.getApplication();
		LifecycleDispatcher.get().onFragmentAttach(this, activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LifecycleDispatcher.get().onFragmentCreated(this, savedInstanceState);
	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mPreView == null) {
			return createView(inflater, container, savedInstanceState);
		} else {
			// 由于这里直接返回，createView中的一些刷新数据的操作将不会执行
			return mPreView;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		LifecycleDispatcher.get().onFragmentStarted(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		LifecycleDispatcher.get().onFragmentResumed(this);
		if ((mIsInit) && (mIsVisible)) {
			onLasyLoad();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		LifecycleDispatcher.get().onFragmentPaused(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		LifecycleDispatcher.get().onFragmentStopped(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LifecycleDispatcher.get().onFragmentDestroyed(this);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LifecycleDispatcher.get().onFragmentDetach(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LifecycleDispatcher.get().onFragmentSaveInstanceState(this, outState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LifecycleDispatcher.get().onFragmentActivityCreated(this, savedInstanceState);
	}

	protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			mIsVisible = true;
			if ((mIsInit) && (mIsVisible))
				onLasyLoad();
		} else {
			mIsVisible = false;
		}

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// 已经完成初始化
		mIsInit = true;
		//
		addListeners(view, savedInstanceState);
		//
		initViews(view, savedInstanceState);
		//
		requestOnCreate();
		//
	}

	public void onLasyLoad() {
		LogUtilLj.LLJi("mIsInit:" + mIsInit + ",mIsVisible:" + mIsVisible);
	}

	/**
	 * 添加监听器
	 */
	protected abstract void addListeners(View view, Bundle savedInstanceState);

	/**
	 * 初始化本地数据
	 */
	protected abstract void initViews(View view, Bundle savedInstanceState);

	/**
	 * 在onCreate中请求服务
	 */
	protected abstract void requestOnCreate();

	public <T> void onLoadMoreData(ListView listview, final QuickAdapter<T> adapter) {
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// fling状态转idle一定会使使最后项(getLastVisiblePosition)到达footview,touch也可以使最后项到达footview，但是也有可能只能到达footview的前一项
				if (scrollState == 0) {
					if (view.getLastVisiblePosition() != 0 && view.getLastVisiblePosition() == view.getCount() - 1) {
						LogUtilLj.LLJi("view.getLastVisiblePosition()" + view.getLastVisiblePosition() + ",view.getCount()" + view.getCount());
						if (adapter.isProgressVisible()) {
							doLoadMoreData();
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}
		});
	}

	public void doLoadMoreData() {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// mPreView = getView();
	}

}
