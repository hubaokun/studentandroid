package com.common.library.llj.utils;

import java.text.DecimalFormat;

/**
 * 格式转换类(浮点类型转化成字符串)
 * 
 * @author liulj
 * 
 */
public class FromatUtilLj {
	/**
	 * 1.格式化double类型的数据，无四舍五入
	 * 
	 * @param fromat
	 *            格式如00.00，##.00，区别是在格式位数多余的情况下##将会使多余的位数空着，00将会使多余的位数补上0。如果number位数多，将会无条件舍去
	 * @param number
	 *            要转换的数字
	 * @return 对应格式的字符串
	 */
	public static String getDecimalString(String fromat, double number) {
		DecimalFormat df1 = new DecimalFormat(fromat);
		return df1.format(number);
	}

	/**
	 * 2.格式化double类型的数据，无四舍五入
	 * 
	 * @param fromat
	 *            格式如00.00，##.00，区别是在格式位数多余的情况下##将会使多余的位数空着，00将会使多余的位数补上0。如果number位数多，将会无条件舍去
	 * @param number
	 *            要转换的数字
	 * @return 对应格式的字符串
	 */
	public static String getDecimalString(String fromat, long number) {
		DecimalFormat df1 = new DecimalFormat(fromat);
		return df1.format(number);
	}
}
