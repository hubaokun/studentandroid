/**
 * 
 */
package com.common.library.llj.views;

import android.content.Context;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.common.library.llj.R;
import com.common.library.llj.base.BaseDialog;

/**
 * 
 * @author liulj
 * 
 */
public class LoadingDialog extends BaseDialog {

	private Context mContext;
	private boolean mCancelable;
	private ImageView mImageView;
	private static LoadingDialog mLoadingDialog;

	// /**
	// *
	// * @param context
	// */
	// public static void getInstanse(Context context) {
	// if (mLoadingDialog == null) {
	// synchronized (LoadingDialog.class) {
	// if (mLoadingDialog == null) {
	// mLoadingDialog = new LoadingDialog(context);
	// }
	// }
	// }
	// }

	public LoadingDialog(Context context) {
		super(context, R.style.dim_dialog);
		mContext = context;
	}

	public LoadingDialog(Context context, boolean cancelable) {
		super(context, R.style.dim_dialog);
		mContext = context;
		mCancelable = cancelable;
		setCancelable(mCancelable);
		setCanceledOnTouchOutside(false);
	}

	@Override
	public void show() {
		super.show();
		// 需要调用系统的super.show()来调用onCreate来实例化view
		if (mImageView != null)
			mImageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.progress_anim));
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (mImageView != null)
			mImageView.clearAnimation();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.loading_dialog_layout;
	}

	@Override
	protected void findViews() {
		mImageView = (ImageView) findViewById(R.id.loading_image);

	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.CENTER);

	}

}
