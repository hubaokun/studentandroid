package com.common.library.llj.base;

import com.common.library.llj.adapterhelp.QuickAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Base中需要用到的一些方法
 * 
 * @author liulj
 * 
 */
public interface IBaseActivity {
	/**
	 * 
	 */
	public abstract int getLayoutId();

	public abstract View getLayoutView();

	/**
	 * 获取界面传递数据
	 */
	public abstract void getIntentData();

	/**
	 * 初始化布局中的空间，首先要调用setContentView
	 */
	public abstract void findViews(Bundle savedInstanceState);

	/**
	 * 添加监听器
	 */
	public abstract void addListeners();

	/**
	 * 初始化本地数据
	 */
	public abstract void initViews();

	/**
	 * 在onCreate中请求服务
	 */
	public abstract void requestOnCreate();

	/**
	 * 判断PullToRefreshListView滑倒底部
	 * 
	 * @param listview
	 */
	public void onListViewReachBottom(final ListView listview);

	/**
	 * 
	 * @param <T>
	 * @param listview
	 */
	public <T> void onLoadMoreData(final ListView listview, QuickAdapter<T> adapter);

	/**
	 * 判断网络是否可用
	 * 
	 * @return true可用false不可用
	 */
	public boolean isNetworkAvailable();

	/**
	 * 判断用户是否登录
	 * 
	 * @return true登录false没有登录
	 */
	public boolean isUserLogin();

	/**
	 * 判断用户是否登录,用于startForResult的请求
	 * 
	 * @return true登录false没有登录
	 */
	public boolean isLoginForResult();

	/**
	 * 弹共通的toast
	 * 
	 * @param content
	 *            提示的内容
	 */
	public void showToast(String content);

	/**
	 * 显示网络异常的提示
	 */
	public void showNetErrToast();

	/**
	 * 不带数据的界面跳转
	 * 
	 * @param cls
	 */
	public void startMyActivity(Class<?> cls);

	/**
	 * 带数据的界面跳转
	 * 
	 * @param cls
	 * @param bundle
	 */
	public void startMyActivity(Class<?> cls, String key, Bundle bundle);

	/**
	 * 设置textview的文本，判断了非空
	 * 
	 * @param textView
	 * @param destination
	 */
	public void setText(TextView textView, String destination);

	/**
	 * 设置textview的文本，判断了非空
	 * 
	 * @param destination
	 * @param defult
	 */
	public void setText(TextView textView, String destination, String defult);

	/**
	 * 判断跳内页还是外页面
	 * 
	 * @param title
	 *            标题
	 * @param link
	 *            链接
	 */
	public void gotoInerPage(String title, String link);

	/**
	 * 缓存信息到本地文件
	 * 
	 * @param key
	 *            SharedPreferences的key
	 * @param result
	 *            网络获取的信息
	 */

	public void saveJsonData(String key, BaseReponse result);

	/**
	 * 从本地缓存文件中获取数据
	 * 
	 * @param key
	 *            SharedPreferences的key
	 * @param classOfT
	 *            gson反射的对象
	 * @return
	 */
	public <T> T getJsonData(String key, Class<T> classOfT);

	/**
	 * 清除缓存
	 */
	public void cleanCache();

	public String getFileSize();
}
