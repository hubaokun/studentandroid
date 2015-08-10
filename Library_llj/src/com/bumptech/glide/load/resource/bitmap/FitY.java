package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.utils.BitmapUtilLj;

/**
 * 
 * @author liulj
 * 
 */
public class FitY extends BitmapTransformation {
	public FitY(Context context) {
		super(context);
	}

	public FitY(BitmapPool bitmapPool) {
		super(bitmapPool);
	}

	@Override
	public String getId() {
		return "FitY.com.bumptech.glide.load.resource.bitmap";
	}

	@Override
	protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
		return BitmapUtilLj.scaleBitmap3(toTransform, BaseApplication.DISPLAY_WIDTH);
	}
}
