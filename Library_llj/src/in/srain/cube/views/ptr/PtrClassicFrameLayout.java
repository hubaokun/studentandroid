package in.srain.cube.views.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import com.common.library.llj.utils.LogUtilLj;

public class PtrClassicFrameLayout extends PtrFrameLayout {

	private PtrClassicDefaultHeader mPtrClassicHeader;
	private MySimpleOnGestureListener mSimpleOnGestureListener;
	private GestureDetector mGestureDetector;

	public PtrClassicFrameLayout(Context context) {
		super(context);
		initViews();
	}

	public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews();
	}

	private void initViews() {
		mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
		setHeaderView(mPtrClassicHeader);
		addPtrUIHandler(mPtrClassicHeader);

		mSimpleOnGestureListener = new MySimpleOnGestureListener();
		mGestureDetector = new GestureDetector(mSimpleOnGestureListener);
	}

	public PtrClassicDefaultHeader getHeader() {
		return mPtrClassicHeader;
	}

	/**
	 * Specify the last update time by this key string
	 * 
	 * @param key
	 */
	public void setLastUpdateTimeKey(String key) {
		if (mPtrClassicHeader != null) {
			mPtrClassicHeader.setLastUpdateTimeKey(key);
		}
	}

	/**
	 * Using an object to specify the last update time.
	 * 
	 * @param object
	 */
	public void setLastUpdateTimeRelateObject(Object object) {
		if (mPtrClassicHeader != null) {
			mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent e) {
		if (mGestureDetector.onTouchEvent(e)) {
			setEnabled(false);
		} else {
			setEnabled(true);
		}
		return super.dispatchTouchEvent(e);
	}

	private class MySimpleOnGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			if (Math.abs(distanceX) > Math.abs(distanceY)) {
				return true;
			} else {
				return false;
			}
		}

	}
}
