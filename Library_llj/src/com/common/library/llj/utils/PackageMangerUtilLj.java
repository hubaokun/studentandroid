package com.common.library.llj.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * 版本操作类
 * 
 * @author liulj
 * 
 */
public class PackageMangerUtilLj {
	/**
	 * 1.这个用来判别版本新旧，安装的时候会验证，新版本必须比旧的大
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersionCode(Context context) {
		String versionCode = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode + "";
			if (versionCode == null || versionCode.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionCode;
	}

	/**
	 * 2.其实这个才是版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		String versionCode = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionName;
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionCode;
	}
}
