package com.common.library.llj.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.text.TextUtils;

/**
 * 判断应用运行在前台或者后台
 * 
 * @author liulj
 * 
 */
public class ActivityManagerUtilLj {
	/**
	 * 1.判断当前任务是否在任务栈的前台
	 * 
	 * @param context
	 *            上下文对象
	 * @return true 前台 false 后台
	 */
	@SuppressWarnings("deprecation")
	public static boolean isApplicationInForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 获取当前活动的task栈
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo != null && !tasksInfo.isEmpty()) {
			if (context.getApplicationInfo().packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 2.判断当前运行在前台的任务中的最前端activity是否是该className
	 * 
	 * @param context
	 *            上下文对象
	 * @param className
	 *            需要判断的className
	 * @return true 该名字的activity在task的最上面，反之则反
	 */
	@SuppressWarnings("deprecation")
	public static boolean isTopActivityInTask(Context context, String className) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo != null && !tasksInfo.isEmpty()) {
			if (className.equals(tasksInfo.get(0).topActivity.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 3.判断当前运行在前台的任务栈中的最前端activity是否在以下列表中
	 * 
	 * @param context
	 *            上下文对象
	 * @param className
	 *            需要判断的className
	 * @return true 顶端activity在列表中
	 */
	@SuppressWarnings("deprecation")
	public static boolean isTopActivityInList(Context context, List<String> nameList) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo != null && tasksInfo.size() > 0) {
			for (String classname : nameList) {
				if (classname == tasksInfo.get(0).topActivity.getClassName()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 4.判断传入的名字是否在当前所有运行的进程中
	 * 
	 * @param context
	 *            上下文对象
	 * @param processName
	 *            传入的进程名
	 * @return true 闯入的名字是其中的一个进程的名字
	 */
	public static boolean isRunningProcess(Context context, String processName) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 获得进程pid
		int pid = android.os.Process.myPid();
		// 获得运行的所有进程
		List<RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();
		if (processInfoList != null && !processInfoList.isEmpty()) {
			for (RunningAppProcessInfo processInfo : processInfoList) {
				if (processInfo != null && processInfo.pid == pid && TextUtils.equals(processName, processInfo.processName)) {
					return true;
				}
			}
		}
		return false;
	}

}
