package com.common.library.llj.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.library.llj.R;

/**
 * 通用自定义顶部栏
 * 
 * @author llj 14/8/30
 * 
 */
public class CommonTitlebar extends FrameLayout {
	private LinearLayout mContain;
	private TextView mLeftTextView;
	private TextView mCenterTextView;
	private TextView mRightTextView;
	private View mLineView;

	public CommonTitlebar(Context context) {
		super(context);
		initViews(context, null);
	}

	public CommonTitlebar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context, attrs);
	}

	public CommonTitlebar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initViews(context, attrs);
	}

	private void initViews(Context context, AttributeSet attrs) {
		LayoutInflater.from(getContext()).inflate(R.layout.common_titlebar_layout, this);
		mContain = (LinearLayout) findViewById(R.id.li_contain111);
		mLeftTextView = (TextView) findViewById(R.id.tv_left_text);
		mCenterTextView = (TextView) findViewById(R.id.tv_center_text);
		mRightTextView = (TextView) findViewById(R.id.tv_right_text);
		mLineView = findViewById(R.id.v_line);
		initAttrs(context, attrs);
	}

	@SuppressWarnings("deprecation")
	private void initAttrs(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTitlebar);
			// 设置比重值
			LinearLayout.LayoutParams mLeftLiParams = (android.widget.LinearLayout.LayoutParams) mLeftTextView.getLayoutParams();
			LinearLayout.LayoutParams mRightLiParams = (android.widget.LinearLayout.LayoutParams) mRightTextView.getLayoutParams();
			LinearLayout.LayoutParams mCenterTextViewParams = (android.widget.LinearLayout.LayoutParams) mCenterTextView.getLayoutParams();

			mLeftLiParams.weight = typedArray.getFloat(R.styleable.CommonTitlebar_titlebar_lefttext_weight, 1);
			mRightLiParams.weight = typedArray.getFloat(R.styleable.CommonTitlebar_titlebar_righttext_weight, 1);
			mCenterTextViewParams.weight = typedArray.getFloat(R.styleable.CommonTitlebar_titlebar_centertext_weight, 4);

			// LinearLayout.LayoutParams mlp = (android.widget.LinearLayout.LayoutParams) mContain.getLayoutParams();
			mContain.getLayoutParams().height = typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_li_contain_height, 88);
			// mContain.setLayoutParams(mlp);
			mLeftTextView.setLayoutParams(mLeftLiParams);
			mRightTextView.setLayoutParams(mRightLiParams);
			mCenterTextView.setLayoutParams(mCenterTextViewParams);
			// 设置左右textpadding值
			mLeftTextView.setPadding(typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_lefttext_padding_left, 0), 0, 0, 0);
			mRightTextView.setPadding(typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_righttext_padding_left, 16), 0, 0, 0);
			mRightTextView.setPadding(0, 0, typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_righttext_padding_right, 0), 0);
			//
			mLeftTextView.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_lefttext_drawable_padding, 0));
			mRightTextView.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_righttext_drawable_padding, 0));
			// 设置text文字
			mLeftTextView.setText(typedArray.getString(R.styleable.CommonTitlebar_titlebar_lefttext));
			mCenterTextView.setText(typedArray.getString(R.styleable.CommonTitlebar_titlebar_centertext));
			mRightTextView.setText(typedArray.getString(R.styleable.CommonTitlebar_titlebar_righttext));
			// 设置text大小
			// 默认传入的是sp单位，由于这里textSize已经的px单位，所以需要制定传入的单位为px
			mLeftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_lefttext_size, 24));
			mCenterTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_centertext_size, 24));
			mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.CommonTitlebar_titlebar_righttext_size, 24));
			// 设置textcolor
			if (typedArray.getColorStateList(R.styleable.CommonTitlebar_titlebar_lefttext_color) != null)
				mLeftTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonTitlebar_titlebar_lefttext_color));
			if (typedArray.getColorStateList(R.styleable.CommonTitlebar_titlebar_centertext_color) != null)
				mCenterTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonTitlebar_titlebar_centertext_color));
			if (typedArray.getColorStateList(R.styleable.CommonTitlebar_titlebar_righttext_color) != null)
				mRightTextView.setTextColor(typedArray.getColorStateList(R.styleable.CommonTitlebar_titlebar_righttext_color));
			// 设置左右drawable和中间text的background
			if (typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_lefttext_drawable_left) != null) {
				Drawable leftDrawable = typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_lefttext_drawable_left);
				mLeftTextView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
			}
			//
			if (typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_centertext_background_drawable) != null)
				mCenterTextView.setBackgroundDrawable(typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_centertext_background_drawable));
			//
			if (typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_righttext_drawable_right) != null) {
				Drawable rightDrawable = typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_righttext_drawable_right);
				mRightTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
			}
			//
			if (typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_line_background) != null)
				mLineView.setBackgroundDrawable(typedArray.getDrawable(R.styleable.CommonTitlebar_titlebar_line_background));
			// 设置text显示隐藏
			mLeftTextView.setVisibility(typedArray.getInt(R.styleable.CommonTitlebar_titlebar_lefttext_visibility, 0));
			mCenterTextView.setVisibility(typedArray.getInt(R.styleable.CommonTitlebar_titlebar_centertext_visibility, 0));
			mRightTextView.setVisibility(typedArray.getInt(R.styleable.CommonTitlebar_titlebar_righttext_visibility, 0));
			mLineView.setVisibility(typedArray.getInt(R.styleable.CommonTitlebar_titlebar_line_visibility, 0));
			typedArray.recycle();
		}

	}

	public TextView getLeftTextView() {
		return mLeftTextView;
	}

	public TextView getCenterTextView() {
		return mCenterTextView;
	}

	public TextView getRightTextView() {
		return mRightTextView;
	}

	public void setLeftTextOnClickListener(OnClickListener onClickListener) {
		mLeftTextView.setOnClickListener(onClickListener);
	}

	public void setRightTextOnClickListener(OnClickListener onClickListener) {
		mRightTextView.setOnClickListener(onClickListener);
	}

	public void setCenterTextOnClickListener(OnClickListener onClickListener) {
		mCenterTextView.setOnClickListener(onClickListener);
	}

	public void setAllText(String left, String center, String right) {
		mLeftTextView.setText(left);
		mCenterTextView.setText(center);
		mRightTextView.setText(right);
	}
}
