package com.common.library.llj.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * <p>
 * <li>
 * BigDecimal.ROUND_HALF_UP,四舍五入，一般用这个</li>
 * <li>
 * BigDecimal.ROUND_HALF_DOWN,四舍五舍</li>
 * <li>
 * BigDecimal.ROUND_UP,舍入远离零的舍入模式。在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。注意,此舍入模式始终不会减少计算值的大小。</li>
 * <li>
 * BigDecimal.ROUND_DOWN,接近零的舍入模式。在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1,即截短)。注意,此舍入模式始终不会增加计算值的大小。</li>
 * <li>
 * BigDecimal.ROUND_CEILING,接近正无穷大的舍入模式。如果 BigDecimal 为正,则舍入行为与 ROUND_UP 相同;如果为负,则舍入行为与 ROUND_DOWN 相同。注意,此舍入模式始终不会减少计算值。</li>
 * <li>
 * BigDecimal.ROUND_FLOOR,接近负无穷大的舍入模式。如果 BigDecimal 为正,则舍入行为与 ROUND_DOWN 相同;如果为负,则舍入行为与 ROUND_UP 相同。注意,此舍入模式始终不会增加计算值。</li>
 * </p>
 * 
 * @author liulj
 * 
 */
public class DecimalUtil {
	/**
	 * 设置四舍五入，并保留小数位数
	 * 
	 * @param str
	 *            需要转换的数字
	 * @param remainNum
	 *            需要保留的位数
	 *            <p>
	 *            00204.5455353保留两位是204.55
	 *            </p>
	 * @return 转换后的字符串，小数左边多余的0会自动去掉
	 */
	public static String formatHalfUp(long needFormat, int remainNum) {
		BigDecimal bigDecimal = new BigDecimal(needFormat);
		return bigDecimal.setScale(remainNum, BigDecimal.ROUND_HALF_UP).toPlainString();

	}

	/**
	 * 设置四舍五入，并保留小数位数
	 * 
	 * @param str
	 *            需要转换的数字
	 * @param remainNum
	 *            需要保留的位数
	 *            <p>
	 *            00204.5455353保留两位是204.55
	 *            </p>
	 * @return 转换后的字符串，小数左边多余的0会自动去掉
	 */
	public static String formatHalfUp(double needFormat, int remainNum) {
		BigDecimal bigDecimal = new BigDecimal(needFormat);
		return bigDecimal.setScale(remainNum, BigDecimal.ROUND_HALF_UP).toPlainString();

	}

	/**
	 * #.##，00.00(小数点后面有几位就保留几位，先会进行四舍五入后截取)
	 * <p>
	 * #.##,可以确保小数点左边不存在多余的0
	 * </p>
	 * <p>
	 * 000000.50823f使用默认转换后是0.51
	 * </p>
	 * 
	 * @param formatType
	 * @param str
	 * @return
	 */
	public static String format(int formatType, String str) {
		String pattern = "#.##";
		switch (formatType) {
		case 1:
			pattern = "00.00";
			break;
		}
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		return decimalFormat.format(0000010.50823f);
	}

	/**
	 * 使用java正则表达式去掉小数点后面多余的.与0
	 * 
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}
}
