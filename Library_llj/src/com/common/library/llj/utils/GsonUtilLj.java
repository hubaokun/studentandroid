package com.common.library.llj.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author liulj
 * 
 */
public class GsonUtilLj {
	/**
	 * 1.解析成指定对象
	 * 
	 * @param jsonString
	 *            里面必须是一个对象
	 * @param cls
	 * @return
	 */
	public static <T> T getObject(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 2.解析成指定对象的集合
	 * 
	 * @param jsonString
	 *            里面必须是一个数组或者集合
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getList(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 3.解析成字符串集合
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<String> getStringList(String jsonString) {
		List<String> list = new ArrayList<String>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 4.解析成hashmap
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getHashMap(String jsonString) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			map = gson.fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 5.
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> listKeyMap(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
