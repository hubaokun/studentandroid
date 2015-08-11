package com.common.library.llj.base;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.common.library.llj.R;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.lifecycle.LifecycleDispatcher;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.BitmapUtilLj;
import com.common.library.llj.utils.FileUtilLj;
import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.ToastUtilLj;
import com.common.library.llj.views.LoadingDialog;

/**
 * 
 * @author liulj
 * 
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements IBaseActivity {
	public BaseFragmentActivity mBaseFragmentActivity;
	public BaseApplication mBaseApplication;
	public LoadingDialog mLoadingDialog;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBaseFragmentActivity = this;
		mBaseApplication = (BaseApplication) getApplication();
		mBaseApplication.addCurrentActivity(this);
		initLoadingDialog();
		getIntentData();
		if (getLayoutView() != null) {
			setContentView(getLayoutView());
		} else {
			setContentView(getLayoutId());
		}
		findViews(savedInstanceState);
		addListeners();
		initViews();
		requestOnCreate();
		LifecycleDispatcher.get().onActivityCreated(this, savedInstanceState);
	}

	private void initLoadingDialog() {
		mLoadingDialog = new LoadingDialog(this);
		mLoadingDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				AsyncHttpClientUtil.get().cancelRequests(mBaseFragmentActivity, true);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		LifecycleDispatcher.get().onActivityStarted(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LifecycleDispatcher.get().onActivityResumed(this);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		LifecycleDispatcher.get().onActivityPaused(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		LifecycleDispatcher.get().onActivityStopped(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBaseApplication.removeCurrentActivity(this);
		LifecycleDispatcher.get().onActivityDestroyed(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LifecycleDispatcher.get().onActivitySaveInstanceState(this, outState);
	}

	@Override
	public void getIntentData() {
	};

	@Override
	public abstract int getLayoutId();

	@Override
	public View getLayoutView() {
		return null;
	}

	@Override
	public abstract void findViews(Bundle savedInstanceState);

	@Override
	public abstract void addListeners();

	@Override
	public abstract void initViews();

	@Override
	public abstract void requestOnCreate();

	@Override
	public void onListViewReachBottom(ListView listview) {
		// 最后一条上拉时的提醒
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// fling状态转idle一定会使使最后项(getLastVisiblePosition)到达footview,touch也可以使最后项到达footview，但是也有可能只能到达footview的前一项
				if (scrollState == 0) {
					if (view.getLastVisiblePosition() != 0 && view.getLastVisiblePosition() == view.getCount() - 1) {
						showToast("无更多数据！");
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}
		});
	}

	@Override
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
	public boolean isNetworkAvailable() {

		return false;
	}

	@Override
	public boolean isUserLogin() {
		return false;
	}

	@Override
	public boolean isLoginForResult() {

		return false;
	}

	@Override
	public void showToast(String content) {
		if (!TextUtils.isEmpty(content))
			ToastUtilLj.show(this, content, Toast.LENGTH_SHORT);
	}

	@Override
	public void showNetErrToast() {

	}

	@Override
	public void startMyActivity(Class<?> cls) {
		startActivity(new Intent(mBaseFragmentActivity, cls));
	}

	@Override
	public void startMyActivity(Class<?> cls, String key, Bundle bundle) {
		Intent intent = new Intent(mBaseFragmentActivity, cls);
		intent.putExtra(key, bundle);
		startActivity(intent);
	}

	@Override
	public void setText(TextView textView, String destination) {
		if (!TextUtils.isEmpty(destination))
			textView.setText(destination);
		else
			textView.setText("");
	}

	@Override
	public void setText(TextView textView, String destination, String defult) {
		if (TextUtils.isEmpty(destination)) {
			textView.setText(defult);
		} else {
			textView.setText(destination);
		}
	}

	@Override
	public void gotoInerPage(String title, String link) {

	}

	@Override
	public void saveJsonData(String key, BaseReponse result) {
	}

	@Override
	public <T> T getJsonData(String key, Class<T> classOfT) {

		return null;
	}

	@Override
	public void cleanCache() {
		/**
		 * 清理缓存
		 */
		File cacheFolder = new File(BaseApplication.PIC_PATH);

		// 清理所有子文件
		for (File file : cacheFolder.listFiles()) {
			if (!file.isDirectory())
				file.delete();
		}
		cacheFolder = new File(BaseApplication.TEMP_PATH);

		// 清理所有子文件
		for (File file : cacheFolder.listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		showToast("缓存已清除！");
	}

	@Override
	public String getFileSize() {
		String str = FileUtilLj.formatFileSize(FileUtilLj.getFileSize(new File(BaseApplication.PIC_PATH)));
		return str;
	}

	/**
	 * 点击外面取消输入法如果外层包裹了scrollview则时间会被处理，不会传出到activity中，也就无效了
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (this.getCurrentFocus() != null) {
				if (this.getCurrentFocus().getWindowToken() != null) {
					imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 下载图片，使用共通的Holder背景
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @param imageView
	 */
	public void loadImageDefault(String imageUrl, int width, int height, ImageView imageView) {
		// 一定要设置centerCrop()才可以真正获得指定width和height的像素图片，如果不设置那么返回的resource并不是指定尺寸
		if (!TextUtils.isEmpty(imageUrl) && imageView != null)
			Glide.with(this).load(imageUrl).asBitmap().override(width, width).centerCrop().into(new BitmapImageViewTarget(imageView) {
				@Override
				protected void setResource(Bitmap resource) {
					if (resource != null) {
						LogUtilLj.LLJi("bitmap:" + resource.getWidth() + "*" + resource.getHeight());
						view.setScaleType(ScaleType.CENTER_CROP);
						view.setImageBitmap(resource);
					}
				}
			});

	}

	/**
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @param imageView
	 */
	public void loadFileToImage(File file, int width, int height, ImageView imageView) {
		// 一定要设置centerCrop()才可以真正获得指定width和height的像素图片，如果不设置那么返回的resource并不是指定尺寸
		if (file != null && file.exists() && imageView != null)
			Glide.with(this).load(file).crossFade().override(width, width).centerCrop().into(imageView);

	}

	/**
	 * 下载图片，没有Holder背景
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @param imageView
	 */
	public void loadImageNoHolder(String imageUrl, int width, int height, ImageView imageView) {
		// 一定要设置centerCrop()才可以真正获得指定width和height的像素图片，如果不设置那么返回的resource并不是指定尺寸
		if (!TextUtils.isEmpty(imageUrl) && imageView != null)
			Glide.with(this).load(imageUrl).asBitmap().override(width, width).centerCrop().into(new BitmapImageViewTarget(imageView) {
				@Override
				protected void setResource(Bitmap resource) {
					if (resource != null) {
						LogUtilLj.LLJi("bitmap:" + resource.getWidth() + "*" + resource.getHeight());
						view.setScaleType(ScaleType.CENTER_CROP);
						view.setImageBitmap(resource);
					}
				}
			});

	}

	/**
	 * 下载图片，没有Holder背景
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @param imageView
	 */
	public void loadBackground(String imageUrl, int width, int height, View target) {
		// 一定要设置centerCrop()才可以真正获得指定width和height的像素图片，如果不设置那么返回的resource并不是指定尺寸
		if (!TextUtils.isEmpty(imageUrl) && target != null)
			Glide.with(mBaseFragmentActivity).load(imageUrl).asBitmap().override(width, height).centerCrop().into(new ViewTarget<View, Bitmap>(target) {

				@SuppressWarnings("deprecation")
				@Override
				public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
					if (resource != null) {
						view.setBackgroundDrawable(new BitmapDrawable(getResources(), resource));
					}
				}
			});
	}

	/**
	 * 下载图片，需要提供resHolder和resErr
	 * 
	 * @param imageUrl
	 * @param resHolder
	 * @param resErr
	 * @param width
	 * @param height
	 * @param imageView
	 */
	public void loadImage(String imageUrl, int resHolder, int resErr, int width, int height, ImageView imageView) {
		// 一定要设置centerCrop()才可以真正获得指定width和height的像素图片，如果不设置那么返回的resource并不是指定尺寸
		if (!TextUtils.isEmpty(imageUrl) && imageView != null)
			Glide.with(this).load(imageUrl).asBitmap().placeholder(resHolder).error(resErr).override(width, width).centerCrop().into(new BitmapImageViewTarget(imageView) {
				@Override
				protected void setResource(Bitmap resource) {
					if (resource != null) {
						LogUtilLj.LLJi("bitmap:" + resource.getWidth() + "*" + resource.getHeight());
						view.setScaleType(ScaleType.CENTER_CROP);
						view.setImageBitmap(resource);
					}
				}
			});

	}

	/**
	 * 下载圆形头像，已经设置默认的背景
	 * 
	 * @param imageUrl
	 * @param width
	 * @param height
	 * @param imageView
	 */
	public void loadHeadImage(String imageUrl, int width, int height, ImageView imageView) {
		// 一定要设置centerCrop()才可以真正获得指定width和height的像素图片，如果不设置那么返回的resource并不是指定尺寸
		if (!TextUtils.isEmpty(imageUrl) && imageView != null)
			Glide.with(this).load(imageUrl).asBitmap().placeholder(R.drawable.login_head_img).override(width, height).centerCrop().into(new BitmapImageViewTarget(imageView) {
				@Override
				protected void setResource(Bitmap resource) {
					if (resource != null) {
						LogUtilLj.LLJi("bitmap:" + resource.getWidth() + "*" + resource.getHeight());
						view.setScaleType(ScaleType.CENTER_CROP);
						view.setImageBitmap(BitmapUtilLj.getRoundBitmap(resource));
					}
				}
			});
		else
			imageView.setImageResource(R.drawable.login_head_img);
	}
}
