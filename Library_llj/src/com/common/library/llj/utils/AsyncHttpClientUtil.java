package com.common.library.llj.utils;

import org.apache.http.Header;

import android.content.Context;

import com.common.library.llj.base.BaseReponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class AsyncHttpClientUtil {
	private static AsyncHttpClient mAsyncHttpClient = null;
	private static AsyncHttpClientUtil asyncHttpClientUtil = null;

	/**
	 * 
	 * @return
	 */
	public static AsyncHttpClientUtil get() {
		if (asyncHttpClientUtil == null || mAsyncHttpClient == null) {
			synchronized (AsyncHttpClientUtil.class) {
				if (asyncHttpClientUtil == null) {
					asyncHttpClientUtil = new AsyncHttpClientUtil();
				}
				if (mAsyncHttpClient == null) {
					mAsyncHttpClient = new AsyncHttpClient();
				}
			}
		}
		return asyncHttpClientUtil;

	}

	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param reponseClass
	 * @param myResponseHandler
	 */
	public <T extends BaseReponse> void get(final Context context, String url, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
		mAsyncHttpClient.setTimeout(30000);
		mAsyncHttpClient.get(context, url, myResponseHandler.setParams(myResponseHandler.getParams()), new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				LogUtilLj.LLJi("url:" + getRequestURI());
				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
				if (myResponseHandler != null) {
					myResponseHandler.onStart();
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				LogUtilLj.LLJi("onSuccess:" + responseString);
				if (responseString != null) {
					T baseReponse = new Gson().fromJson(responseString, reponseClass);
					if (baseReponse != null) {
						switch (baseReponse.getCode()) {
						case 1:
							if (myResponseHandler != null) {
								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
							}
							break;
						default:
							if (myResponseHandler != null) {
								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
							}
							break;
						}
					}
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
				if (myResponseHandler != null) {
					myResponseHandler.onFinish();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				LogUtilLj.LLJi("onFailure:" + responseString);
				LogUtilLj.LLJi(throwable);
				ToastUtilLj.show(context, "网络状况不佳，请稍后再试！");
				if (myResponseHandler != null) {
					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
				}
			}
		});
	}

	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param reponseClass
	 * @param myResponseHandler
	 */
	public <T extends BaseReponse> void post(final Context context, String url, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
		mAsyncHttpClient.setTimeout(2000000);
		mAsyncHttpClient.post(context, url, myResponseHandler.setParams(myResponseHandler.getParams()), new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				LogUtilLj.LLJi("url:" + getRequestURI());
				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
				super.onStart();
				if (myResponseHandler != null) {
					myResponseHandler.onStart();
				}
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, String responseString) {
				LogUtilLj.LLJi("onSuccess:" + responseString);
				if (responseString != null) {
					T baseReponse = new Gson().fromJson(responseString, reponseClass);
					if (baseReponse != null) {
						switch (baseReponse.getCode()) {
						case 1:
							if (myResponseHandler != null) {
								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
							}
							break;

						default:
							if (myResponseHandler != null) {
								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
							}
							break;
						}
					}
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
				if (myResponseHandler != null) {
					myResponseHandler.onFinish();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				LogUtilLj.LLJi("onFailure:" + responseString);
				LogUtilLj.LLJi(throwable);
				ToastUtilLj.show(context, "网络状况不佳，请稍后再试！");
				if (myResponseHandler != null) {
					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
				}
			}
		});
	}

	public void cancelRequests(final Context context, final boolean mayInterruptIfRunning) {
		mAsyncHttpClient.cancelRequests(context, mayInterruptIfRunning);
	}

	public void cancelAllRequests(boolean mayInterruptIfRunning) {
		mAsyncHttpClient.cancelAllRequests(mayInterruptIfRunning);

	}

}
