package com.common.library.llj.base;

import com.common.library.llj.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialogFragment extends DialogFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, R.style.no_dim_dialog);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return onGetView(inflater, container, savedInstanceState);
	}

	public abstract View onGetView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

	@Override
	public void onStart() {
		setWindowParam();
		super.onStart();
	}

	/**
	 * 3.设置window属性
	 */
	protected abstract void setWindowParam();

	/**
	 * 
	 * @param resId
	 * @param gravity
	 */
	public void setWindowParams(int gravity) {
		setWindowParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, gravity);
	}

	/**
	 * 宽是match,高自己定义
	 * 
	 * @param height
	 * @param gravity
	 */
	public void setWindowParams(int height, int gravity) {
		setWindowParams(LayoutParams.MATCH_PARENT, height, gravity);
	}

	/**
	 * 在设置 设置dialog的一些属性
	 * 
	 * @param width
	 *            一般布局和代码这里都设置match,要设置边距的直接布局里调好
	 * @param height
	 *            一般布局height设置为wrap，这样可以调整dialog的上中下位置，要固定(非上中下)位置的直接在布局中调整， 设置match后，软键盘不会挤压布局
	 * @param gravity
	 *            设置match后，此属性无用
	 * 
	 */
	public void setWindowParams(int width, int height, int gravity) {
		Window window = getDialog().getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
		// params.alpha = 1f;
		// 窗口的背景，0为透明，1为全黑
		// params.dimAmount = 0f;
		params.width = width;
		params.height = height;
		params.gravity = gravity;
		window.setAttributes(params);
	}
}
