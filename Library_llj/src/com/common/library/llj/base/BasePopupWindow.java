package com.common.library.llj.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 
 * @author liulj
 * 
 */
public class BasePopupWindow extends PopupWindow {
	private Context mContext;
	private float mShowAlpha = 0.5f;

	public BasePopupWindow(Context context) {
		super(context, null);
		mContext = context;
	}

	public BasePopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public BasePopupWindow(View contentView) {
		super(contentView);
	}

	public BasePopupWindow(int width, int height) {
		super(width, height);
	}

	public BasePopupWindow(View contentView, int width, int height, Activity context) {
		super(contentView, width, height);
		mContext = context;
		init(context);
	}

	public BasePopupWindow(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
	}

	private void init(final Activity context) {
		// 在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
		setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// 这个设置了可以按返回键dismiss
		setFocusable(true);
		// 可以点击外面dismiss需要一下两个条件
		setBackgroundDrawable(new ColorDrawable());
		setOutsideTouchable(true);
		// 添加pop窗口关闭事件
		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				backgroundAlpha(context, 1f);

			}
		});
	}

	public void setShowAlpha(float bgAlpha) {
		this.mShowAlpha = bgAlpha;
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		// 设置背景窗口的背景颜色
		backgroundAlpha((Activity) mContext, mShowAlpha);
		super.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(Activity context, float bgAlpha) {
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		context.getWindow().setAttributes(lp);
	}
}
