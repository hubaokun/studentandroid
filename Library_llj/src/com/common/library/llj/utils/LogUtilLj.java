package com.common.library.llj.utils;

import android.util.Log;

/**
 * 
 * @author liulj
 * 
 */
public class LogUtilLj {
	private static boolean DEBUGLLJ = true;
	private static final String LLJ = "LLJ";

	/**
	 * Verbose黑色
	 * 
	 * @param message
	 */
	public static void LLJv(String message) {
		if (DEBUGLLJ)
			Log.v(LLJ, " | Info | " + message);
	}

	/**
	 * Debug蓝色
	 * 
	 * @param message
	 */
	public static void LLJd(String message) {
		if (DEBUGLLJ)
			Log.d(LLJ, " | Error | " + message);
	}

	/**
	 * Info绿色
	 * 
	 * @param message
	 */
	public static void LLJi(String message) {
		if (DEBUGLLJ)
			Log.i(LLJ, " | Info | " + message);
	}

	/**
	 * Info绿色
	 * 
	 * @param message
	 */
	public static void LLJi(Throwable throwable) {
		if (DEBUGLLJ)
			Log.getStackTraceString(throwable);
	}

	/**
	 * Warning黄色
	 * 
	 * @param message
	 */
	public static void LLJw(String message) {
		if (DEBUGLLJ)
			Log.w(LLJ, " | Info | " + message);
	}

	/**
	 * Error红色
	 * 
	 * @param message
	 */
	public static void LLJe(String message) {
		if (DEBUGLLJ)
			Log.e(LLJ, " | Error | " + message);
	}
}
