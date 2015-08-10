package com.common.library.llj.utils;

import java.security.MessageDigest;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 文字处理类
 * 
 * @author llj
 * 
 */
public class TextUtilLj {
	/**
	 * 1.计算出该TextView中文字的长度(像素)
	 * 
	 * @param textView
	 * @param text
	 *            文字
	 * @return
	 */
	public static float getTextViewLength(TextView textView, String text) {
		TextPaint paint = textView.getPaint();
		// 得到使用该paint写上text的时候,像素为多少
		float textLength = paint.measureText(text);
		return textLength;
	}

	/**
	 * 根据宽度去截取字符串，在最后位置显示省略号
	 * 
	 * @param charSequence
	 *            提供的字符串
	 * @param textView
	 *            显示文字的textView
	 * @param availableTextWidth
	 *            三行除去省略号的长度
	 * @return 截取后的字符串
	 */
	public static CharSequence getEndEllipsizeStr(CharSequence charSequence, TextView textView, int availableTextWidth) {
		return TextUtils.ellipsize(charSequence, textView.getPaint(), availableTextWidth, TextUtils.TruncateAt.END);
	}

	/**
	 * MD5加密
	 * 
	 * @param 需要加密的String
	 * @return 加密后String
	 */
	public static final String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}

			return new String(str).toUpperCase();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * MD5加密
	 * 
	 * @param 需要加密的String
	 * @return 加密后String
	 */
	public static final String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}

			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 设置文字后面带标签，字太长固定最大宽度并设置省略号，只显示一行
	 * 
	 * @param context
	 *            上下文
	 * @param otherWidth
	 *            tag的宽度加上那些被占用的宽度
	 * @param str
	 *            需要填充的全部字符串
	 * @param tagWidth
	 *            tag的宽度
	 * @param resId
	 *            tag标签的背景
	 * @param tagStr
	 *            tag标签的文本
	 * @param textTv
	 *            文本框
	 * @param imageTv
	 *            tag文本框
	 */
	public static final void setImageAfterTv(Context context, int otherWidth, String str, int tagWidth, int resId, String tagStr, TextView textTv, TextView imageTv) {
		int diaplayWidth = DisplayUtilLj.getWidthPixels(context);// 屏幕宽分辨率
		float strWidth = getTextViewLength(textTv, str);// 字符串所占长度
		int firstShortWidthPx = diaplayWidth - DisplayUtilLj.dp2px(context, otherWidth);// 第一行可用最小长度
		imageTv.setBackgroundResource(resId);
		imageTv.setText(tagStr);
		textTv.setText(str);
		if (strWidth < firstShortWidthPx) {
			imageTv.setVisibility(View.VISIBLE);
			// 他们两个的父布局要是linearlayout
			LayoutParams params = (LinearLayout.LayoutParams) imageTv.getLayoutParams();
			params.leftMargin = DisplayUtilLj.dp2px(context, 5);
			imageTv.setLayoutParams(params);
			imageTv.setWidth(DisplayUtilLj.dp2px(context, tagWidth));
		} else if (strWidth > firstShortWidthPx) {
			imageTv.setVisibility(View.VISIBLE);
			LayoutParams params = (LinearLayout.LayoutParams) imageTv.getLayoutParams();
			params.leftMargin = DisplayUtilLj.dp2px(context, 5);
			imageTv.setLayoutParams(params);
			imageTv.setWidth(DisplayUtilLj.dp2px(context, tagWidth));
			textTv.setWidth(diaplayWidth - DisplayUtilLj.dp2px(context, otherWidth));
		}
	}
}
