package com.common.library.llj.utils;

import org.apache.http.Header;

import android.content.Context;

import com.common.library.llj.base.BaseReponse;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 
 * @author liulj
 * 
 */
public abstract class MyResponseHandler<T extends BaseReponse> extends TextHttpResponseHandler {
	public RequestParams mRequestParams;

	public MyResponseHandler() {
		mRequestParams = new RequestParams();
	}

	public RequestParams getParams() {
		return mRequestParams;
	}

	public RequestParams setParams(RequestParams requestParams) {
		return requestParams;
	}

	public abstract void onSuccess(int statusCode, Header[] headers, T baseReponse);

	public void onNotSuccess(Context context, int statusCode, Header[] headers, T baseReponse) {

	}

	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, String responseString) {
		// 无用
	}

}
